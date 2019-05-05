package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.project.dto.ProjectAssignment;
import cz.cvut.fit.timetracking.project.exception.ProjectAssignmentNotFoundException;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping("/project-assignments")
public class ProjectAssignmentController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RestModelMapper restModelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectAssignmentDTO> getById(@PathParam("id") Integer id) {
        Optional<ProjectAssignment> projectAssignmentOptional = projectService.findProjectAssignmentById(id);
        ProjectAssignmentDTO projectAssignmentDTO = projectAssignmentOptional.map(p -> restModelMapper.map(p, ProjectAssignmentDTO.class)).orElseThrow(() -> new ProjectAssignmentNotFoundException(id));
        return ResponseEntity.ok(projectAssignmentDTO);
    }

    @PostMapping
    public ResponseEntity<Pro> create(@Valid @RequestBody CreateOrUpdateProjectRequest createOrUpdateProjectRequest) {
        Project project = projectService.create(createOrUpdateProjectRequest.getName(), createOrUpdateProjectRequest.getDescription(), createOrUpdateProjectRequest.getStart(), createOrUpdateProjectRequest.getEnd());
        ProjectDTO result = restModelMapper.map(project, ProjectDTO.class);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody CreateOrUpdateProjectRequest updateProjectRequest) {
        Project updatedProject = projectService.update(id, updateProjectRequest.getName(), updateProjectRequest.getDescription(), updateProjectRequest.getStart(), updateProjectRequest.getEnd());
        ProjectDTO result = restModelMapper.map(updatedProject, ProjectDTO.class);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathParam("id") Integer id) {
        projectService.deleteProjectAssignmentById(id);
        return ResponseEntity.ok().build();
    }

}
