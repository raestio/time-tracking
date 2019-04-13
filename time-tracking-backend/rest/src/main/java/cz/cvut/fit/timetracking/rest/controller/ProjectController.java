package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.project.dto.Project;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectDTO;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectsResponse;
import cz.cvut.fit.timetracking.rest.dto.project.UpdateProjectRequest;
import cz.cvut.fit.timetracking.rest.dto.user.UserDTO;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import cz.cvut.fit.timetracking.user.dto.User;
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
    public ResponseEntity<ProjectDTO> createOrUpdate(@Valid @RequestBody ProjectDTO projectDTO) {
        Project project = restModelMapper.map(projectDTO, Project.class);
        project = projectService.createOrUpdate(project);
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
    public ResponseEntity<ProjectDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody UpdateProjectRequest updateProjectRequest) {
        Optional<Project> project = projectService.findById(id);
        ResponseEntity<ProjectDTO> response = project.map(p -> {
            p.setName(updateProjectRequest.getName());
            p.setDescription(updateProjectRequest.getDescription());
            p.setStart(updateProjectRequest.getStart());
            p.setEnd(updateProjectRequest.getEnd());
            Project updatedProject = projectService.createOrUpdate(p);
            return ResponseEntity.ok(restModelMapper.map(updatedProject, ProjectDTO.class));
        }).orElse(ResponseEntity.notFound().build());
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id) {
        projectService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
