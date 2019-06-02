package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.project.dto.Project;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectDTO;
import cz.cvut.fit.timetracking.rest.dto.workrecord.request.CreateOrUpdateWorkRecordRequest;
import cz.cvut.fit.timetracking.rest.dto.workrecord.WorkRecordDTO;
import cz.cvut.fit.timetracking.rest.dto.workrecord.request.CreateWorkRecordsBulkRequest;
import cz.cvut.fit.timetracking.rest.dto.workrecord.response.JiraAvailabilityResponse;
import cz.cvut.fit.timetracking.rest.dto.workrecord.response.WorkRecordsJiraImportResponse;
import cz.cvut.fit.timetracking.rest.dto.workrecord.response.WorkRecordsResponse;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import cz.cvut.fit.timetracking.rest.utils.TimeUtils;
import cz.cvut.fit.timetracking.security.CurrentUser;
import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecordConflictInfo;
import cz.cvut.fit.timetracking.workrecord.dto.input.WorkRecordInput;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordJiraService;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/work-records")
public class WorkRecordController {
    public static final int DEFAULT_LAST_WORK_RECORD_DAYS = 7;

    @Autowired
    private WorkRecordService workRecordService;

    @Autowired
    private RestModelMapper restModelMapper;

    @Autowired
    private WorkRecordJiraService workRecordJiraService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/bulk")
    public ResponseEntity create(@Valid @RequestBody CreateWorkRecordsBulkRequest request, @CurrentUser UserPrincipal user) {
        workRecordService.create(request.getRequests().stream().map(r -> restModelMapper.map(r, WorkRecordInput.class)).collect(Collectors.toList()));
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("#request.userId == null or hasAuthority('ADMIN') or @securityAccessServiceImpl.hasProjectRole(#request.projectId, 'PROJECT_MANAGER')")
    public ResponseEntity<WorkRecordDTO> create(@Valid @RequestBody CreateOrUpdateWorkRecordRequest request, @CurrentUser UserPrincipal user) {
        WorkRecordDTO result = create(request, request.getUserId() == null ? user.getId() : request.getUserId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or @securityAccessServiceImpl.workRecordIsMineOrHasProjectRole(#id, 'PROJECT_MANAGER')")
    public ResponseEntity<WorkRecordDTO> getById(@PathVariable("id") Integer id) {
        Optional<WorkRecord> workRecordOptional = workRecordService.findById(id);
        ResponseEntity<WorkRecordDTO> response = workRecordOptional.map(u -> ResponseEntity.ok(map(u))).orElseGet(() -> ResponseEntity.notFound().build());
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkRecordDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody CreateOrUpdateWorkRecordRequest request) {
        WorkRecord workRecord = workRecordService.update(id, request.getDateFrom(), request.getDateTo(), request.getDescription(), request.getProjectId(), request.getWorkTypeId());
        return ResponseEntity.ok(map(workRecord));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id) {
        workRecordService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<WorkRecordsResponse> getWorkRecords(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromInclusive,
                                                              @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toExclusive,
                                                              @RequestParam(value = "userId", required = false) Integer userId,
                                                              @CurrentUser UserPrincipal userPrincipal) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate from = TimeUtils.getFrom(fromInclusive, toExclusive, tomorrow.minusDays(DEFAULT_LAST_WORK_RECORD_DAYS), date -> date.minusDays(DEFAULT_LAST_WORK_RECORD_DAYS));
        LocalDate to = TimeUtils.getTo(toExclusive, tomorrow);
        List<WorkRecord> workRecords = workRecordService.findAllBetweenByUserId(from.atStartOfDay(), to.atStartOfDay(), (userId == null ? userPrincipal.getId() : userId));
        WorkRecordsResponse workRecordsResponse = new WorkRecordsResponse();
        workRecordsResponse.setWorkRecords(workRecords.stream().map(this::map).collect(Collectors.toList()));
        return ResponseEntity.ok(workRecordsResponse);
    }

    @GetMapping("/jira/availability")
    public ResponseEntity<JiraAvailabilityResponse> getWorkRecordsFromJiraToImport(@RequestParam(value = "userId", required = false) Integer userId,
                                                                                   @CurrentUser UserPrincipal userPrincipal) {
        boolean isAvailable = workRecordJiraService.isUserAvailableInJira(userId == null ? userPrincipal.getId() : userId);
        JiraAvailabilityResponse jiraAvailabilityResponse = new JiraAvailabilityResponse();
        jiraAvailabilityResponse.setUserAvailable(isAvailable);
        return ResponseEntity.ok(jiraAvailabilityResponse);
    }

    @GetMapping("/jira")
    public ResponseEntity<WorkRecordsJiraImportResponse> getWorkRecordsFromJiraToImport(@RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromInclusive,
                                                                                        @RequestParam(value = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toExclusive,
                                                                                        @RequestParam(value = "userId") Integer userId) {
        WorkRecordsJiraImportResponse workRecordsJiraImportResponse = getWorkRecordsFromJiraToImportInternal(fromInclusive, toExclusive, userId);
        return ResponseEntity.ok(workRecordsJiraImportResponse);
    }

    @GetMapping("/jira/me")
    public ResponseEntity<WorkRecordsJiraImportResponse> getWorkRecordsFromJiraToImport(@RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromInclusive,
                                                                 @RequestParam(value = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toExclusive,
                                                                 @CurrentUser UserPrincipal userPrincipal) {
        WorkRecordsJiraImportResponse workRecordsJiraImportResponse = getWorkRecordsFromJiraToImportInternal(fromInclusive, toExclusive, userPrincipal.getId());
        return ResponseEntity.ok(workRecordsJiraImportResponse);
    }

    private WorkRecordsJiraImportResponse getWorkRecordsFromJiraToImportInternal(LocalDate fromInclusive, LocalDate toExclusive, Integer userId) {
        List<WorkRecordConflictInfo> workRecordConflictInfos = workRecordJiraService.findWorkRecordsToImport(fromInclusive, toExclusive, userId);
        List<Project> projects = projectService.findAllCurrentlyAssignedProjectsByUserId(userId);

        WorkRecordsJiraImportResponse workRecordsJiraImportResponse = new WorkRecordsJiraImportResponse();
        workRecordsJiraImportResponse.setWorkRecordConflictInfos(workRecordConflictInfos);
        workRecordsJiraImportResponse.setProjects(projects.stream().map(p -> restModelMapper.map(p, ProjectDTO.class)).collect(Collectors.toList()));
        return workRecordsJiraImportResponse;
    }

    private WorkRecordDTO map(WorkRecord workRecord) {
        WorkRecordDTO result = restModelMapper.map(workRecord, WorkRecordDTO.class);
        return result;
    }

    private WorkRecordDTO create(CreateOrUpdateWorkRecordRequest request, Integer userId) {
        WorkRecord workRecord = workRecordService.create(request.getDateFrom(), request.getDateTo(), request.getDescription(), request.getProjectId(), request.getWorkTypeId(), userId);
        WorkRecordDTO result = map(workRecord);
        return result;
    }
}
