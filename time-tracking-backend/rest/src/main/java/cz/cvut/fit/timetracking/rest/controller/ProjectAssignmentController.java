package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectAssignmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/project-assignments")
public class ProjectAssignmentController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectAssignmentDTO> getById(@PathParam("id") Integer id) {

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectAssignmentDTO> deleteById(@PathParam("id") Integer id) {

    }

}
