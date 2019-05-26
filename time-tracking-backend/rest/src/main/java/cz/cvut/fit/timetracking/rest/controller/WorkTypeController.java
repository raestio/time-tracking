package cz.cvut.fit.timetracking.rest.controller;


import cz.cvut.fit.timetracking.project.dto.WorkType;
import cz.cvut.fit.timetracking.project.exception.WorkTypeNotFoundException;
import cz.cvut.fit.timetracking.project.service.WorkTypeService;
import cz.cvut.fit.timetracking.rest.dto.project.WorkTypeDTO;
import cz.cvut.fit.timetracking.rest.dto.project.request.CreateOrUpdateWorkTypeRequest;
import cz.cvut.fit.timetracking.rest.dto.project.response.WorkTypesResponse;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/work-types")
public class WorkTypeController {

    @Autowired
    private WorkTypeService workTypeService;

    @Autowired
    private RestModelMapper restModelMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WorkTypesResponse> getWorkTypes() {
        List<WorkType> projectRoles = workTypeService.findAll();
        WorkTypesResponse workTypesResponse = new WorkTypesResponse();
        workTypesResponse.setWorkTypes(projectRoles.stream().map(this::map).collect(Collectors.toList()));
        return ResponseEntity.ok(workTypesResponse);
    }

    @GetMapping("/{workTypeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WorkTypeDTO> getWorkTypeById(@PathVariable("workTypeId") Integer workTypeId) {
        Optional<WorkType> workType = workTypeService.findById(workTypeId);
        WorkTypeDTO workTypeDTO = map(workType.orElseThrow(() -> new WorkTypeNotFoundException(workTypeId)));
        return ResponseEntity.ok(workTypeDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WorkTypeDTO> createWorkType(@RequestBody @Valid CreateOrUpdateWorkTypeRequest request) {
        WorkType workType = workTypeService.create(request.getName(), request.getDescription());
        return ResponseEntity.ok(map(workType));
    }

    @PutMapping("/{workTypeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<WorkTypeDTO> updateWorkType(@PathVariable("workTypeId") Integer workTypeId, @RequestBody @Valid CreateOrUpdateWorkTypeRequest request) {
        WorkType workType = workTypeService.update(workTypeId, request.getName(), request.getDescription());
        return ResponseEntity.ok(map(workType));
    }

    @DeleteMapping("/{workTypeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity deleteWorkType(@PathVariable("workTypeId") Integer workTypeId) {
        workTypeService.deleteById(workTypeId);
        return ResponseEntity.ok().build();
    }

    private WorkTypeDTO map(WorkType workType) {
        return restModelMapper.map(workType, WorkTypeDTO.class);
    }
}
