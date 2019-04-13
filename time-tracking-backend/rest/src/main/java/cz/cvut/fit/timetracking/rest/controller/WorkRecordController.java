package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.rest.dto.workrecord.UpdateWorkRecordRequest;
import cz.cvut.fit.timetracking.rest.dto.workrecord.WorkRecordDTO;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
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
    public ResponseEntity<WorkRecordDTO> createOrUpdate(@Valid @RequestBody WorkRecordDTO workRecordDTO) {
        WorkRecord workRecord = restModelMapper.map(workRecordDTO, WorkRecord.class);
        workRecord = workRecordService.createOrUpdate(workRecord);
        WorkRecordDTO result = restModelMapper.map(workRecord, WorkRecordDTO.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkRecordDTO> getById(@PathVariable("id") Integer id) {
        Optional<WorkRecord> workRecordOptional = workRecordService.findById(id);
        ResponseEntity<WorkRecordDTO> response = workRecordOptional.map(u -> ResponseEntity.ok(map(u))).orElseGet(() -> ResponseEntity.notFound().build());
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkRecordDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody UpdateWorkRecordRequest updateWorkRecordRequest) {
        Optional<WorkRecord> workRecord = workRecordService.findById(id);
        ResponseEntity<WorkRecordDTO> response = workRecord.map(p -> {
            p.setDateFrom(updateWorkRecordRequest.getDateFrom());
            p.setDateTo(updateWorkRecordRequest.getDateTo());
            p.setDescription(updateWorkRecordRequest.getDescription());
            WorkRecord updatedWorkRecord = workRecordService.createOrUpdate(p);
            return ResponseEntity.ok(restModelMapper.map(updatedWorkRecord, WorkRecordDTO.class));
        }).orElse(ResponseEntity.notFound().build());
        return response;
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
