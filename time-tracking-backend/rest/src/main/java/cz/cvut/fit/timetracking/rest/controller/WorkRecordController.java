package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.rest.dto.workrecord.request.CreateOrUpdateWorkRecordRequest;
import cz.cvut.fit.timetracking.rest.dto.workrecord.WorkRecordDTO;
import cz.cvut.fit.timetracking.rest.dto.workrecord.response.WorkRecordsResponse;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import cz.cvut.fit.timetracking.rest.utils.TimeUtils;
import cz.cvut.fit.timetracking.security.CurrentUser;
import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<WorkRecordDTO> create(@Valid @RequestBody CreateOrUpdateWorkRecordRequest request, @CurrentUser UserPrincipal user) {
        WorkRecordDTO result = create(request, user.getId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
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

    @GetMapping("/jira")
    public ResponseEntity<Object> s() {

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
