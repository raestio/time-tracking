package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.project.dto.ProjectAssignment;
import cz.cvut.fit.timetracking.project.exception.ProjectAssignmentNotFoundException;
import cz.cvut.fit.timetracking.project.service.ProjectAssignmentService;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.rest.dto.project.request.CreateOrUpdateProjectAssignmentRequest;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
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
import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping("/project-assignments")
public class ProjectAssignmentController {

    @Autowired
    private ProjectAssignmentService projectAssignmentService;

    @Autowired
    private RestModelMapper restModelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectAssignmentDTO> getById(@PathParam("id") Integer id) {
        Optional<ProjectAssignment> projectAssignmentOptional = projectAssignmentService.findById(id);
        ProjectAssignmentDTO projectAssignmentDTO = projectAssignmentOptional.map(p -> restModelMapper.map(p, ProjectAssignmentDTO.class)).orElseThrow(() -> new ProjectAssignmentNotFoundException(id));
        return ResponseEntity.ok(projectAssignmentDTO);
    }

    @PostMapping
    public ResponseEntity<ProjectAssignmentDTO> create(@Valid @RequestBody CreateOrUpdateProjectAssignmentRequest request) {
        ProjectAssignment projectAssignment = projectAssignmentService.create(request.getProjectId(), request.getUserId(), request.getValidFrom(), request.getValidTo(), request.getProjectRoleNames());
        ProjectAssignmentDTO result = restModelMapper.map(projectAssignment, ProjectAssignmentDTO.class);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectAssignmentDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody CreateOrUpdateProjectAssignmentRequest request) {
        ProjectAssignment projectAssignment = projectAssignmentService.update(id, request.getProjectId(), request.getUserId(), request.getValidFrom(), request.getValidTo(), request.getProjectRoleNames());
        ProjectAssignmentDTO result = restModelMapper.map(projectAssignment, ProjectAssignmentDTO.class);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathParam("id") Integer id) {
        projectAssignmentService.deleteProjectAssignmentById(id);
        return ResponseEntity.ok().build();
    }

}
