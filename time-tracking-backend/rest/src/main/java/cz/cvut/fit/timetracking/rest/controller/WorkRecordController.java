package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.rest.dto.workrecord.CreateOrUpdateWorkRecordRequest;
import cz.cvut.fit.timetracking.rest.dto.workrecord.WorkRecordDTO;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import cz.cvut.fit.timetracking.security.CurrentUser;
import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/work-records")
public class WorkRecordController {

    @Autowired
    private WorkRecordService workRecordService;

    @Autowired
    private RestModelMapper restModelMapper;

    @PostMapping
    public ResponseEntity<WorkRecordDTO> create(@Valid @RequestBody CreateOrUpdateWorkRecordRequest request, @CurrentUser UserPrincipal user) {
        WorkRecord workRecord = workRecordService.create(request.getDateFrom(), request.getDateTo(), request.getDescription(), request.getProjectId(), request.getWorkTypeId(), user.getId());
        WorkRecordDTO result = map(workRecord);
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

    private WorkRecordDTO map(WorkRecord workRecord) {
        WorkRecordDTO result = restModelMapper.map(workRecord, WorkRecordDTO.class);
        return result;
    }
}
