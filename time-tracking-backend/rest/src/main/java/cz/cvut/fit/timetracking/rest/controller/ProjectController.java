package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.project.dto.Project;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectDTO;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectsResponse;
import cz.cvut.fit.timetracking.rest.dto.project.CreateOrUpdateProjectRequest;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RestModelMapper restModelMapper;

    @PostMapping
    public ResponseEntity<ProjectDTO> create(@Valid @RequestBody CreateOrUpdateProjectRequest createOrUpdateProjectRequest) {
        Project project = projectService.create(createOrUpdateProjectRequest.getName(), createOrUpdateProjectRequest.getDescription(), createOrUpdateProjectRequest.getStart(), createOrUpdateProjectRequest.getEnd());
        ProjectDTO result = restModelMapper.map(project, ProjectDTO.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<ProjectsResponse> getAll() {
        List<Project> projects = projectService.findAll();
        List<ProjectDTO> projectDTOs = projects.stream().map(p -> restModelMapper.map(p, ProjectDTO.class)).collect(Collectors.toList());
        ProjectsResponse projectsResponse = new ProjectsResponse();
        projectsResponse.setProjects(projectDTOs);
        return ResponseEntity.ok(projectsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getById(@PathVariable("id") Integer id) {
        Optional<Project> projectOptional = projectService.findById(id);
        ResponseEntity<ProjectDTO> response = projectOptional.map(u -> ResponseEntity.ok(restModelMapper.map(u, ProjectDTO.class))).orElseGet(() -> ResponseEntity.notFound().build());
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody CreateOrUpdateProjectRequest updateProjectRequest) {
        Project updatedProject = projectService.update(id, updateProjectRequest.getName(), updateProjectRequest.getDescription(), updateProjectRequest.getStart(), updateProjectRequest.getEnd());
        ProjectDTO result = restModelMapper.map(updatedProject, ProjectDTO.class);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id) {
        projectService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/assignments")
    public ResponseEntity<ProjectDTO> getProjectUserAssignments(@PathVariable("id") Integer projectId) {
        List<ProjectAss> projectService.findProjectAssignmentsByProjectId(projectId);
        return null;
    }

    @GetMapping("/{projectId}/assignments/{userId}")
    public ResponseEntity<ProjectDTO> getProjectUserAssignment(@PathVariable("projectId") Integer projectId, @PathVariable("userId") Integer userId) {
        //TODO
        return null;
    }

    @PostMapping("/{projectId}/assignments")
    public ResponseEntity<ProjectDTO> createProjectUserAssignment(@PathVariable("projectId") Integer projectId,  Integer userId) {
        //TODO
        return null;
    }

    @PutMapping("/{projectId}/assignments/{userId}")
    public ResponseEntity<ProjectDTO> updateProjectUserAssignment(@PathVariable("projectId") Integer projectId, @PathVariable("userId") Integer userId) {
        //TODO
        return null;
    }

    @DeleteMapping("/{projectId}/assignments/{userId}")
    public ResponseEntity<ProjectDTO> deleteProjectUserAssignment(@PathVariable("projectId") Integer projectId, @PathVariable("userId") Integer userId) {
        //TODO
        return null;
    }

    @GetMapping("/{projectId}/roles")
    public ResponseEntity<ProjectDTO> getProjectRoles(@PathVariable("projectId") Integer projectId) {
        //TODO
        return null;
    }
}
