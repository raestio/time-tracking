package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.project.dto.Project;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.rest.dto.project.ProjectDTO;
import cz.cvut.fit.timetracking.rest.dto.project.response.ProjectsResponse;
import cz.cvut.fit.timetracking.rest.dto.user.UserRoleDTO;
import cz.cvut.fit.timetracking.rest.dto.user.request.UpdateUserRolesRequest;
import cz.cvut.fit.timetracking.rest.dto.user.UserDTO;
import cz.cvut.fit.timetracking.rest.dto.user.response.UserRolesResponse;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import cz.cvut.fit.timetracking.security.CurrentUser;
import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.dto.UserRole;
import cz.cvut.fit.timetracking.user.dto.UserRoleName;
import cz.cvut.fit.timetracking.user.exception.UserNotFoundException;
import cz.cvut.fit.timetracking.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestModelMapper restModelMapper;

    @Autowired
    private ProjectService projectService;

    @ApiOperation(value = "Get an user by ID", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User with given ID not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@ApiParam(value = "User ID") @PathVariable("id") Integer id, @CurrentUser UserPrincipal userPrincipal) {
        Optional<User> user = userService.findById(id);
        ResponseEntity<UserDTO> response = user.map(u -> ResponseEntity.ok(restModelMapper.map(u, UserDTO.class))).orElseGet(() -> ResponseEntity.notFound().build());
        return response;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody UpdateUserRolesRequest request) {
        User user = userService.updateUserRoles(id, request.getUserRoles().stream().map(role -> restModelMapper.map(role, UserRoleName.class)).collect(Collectors.toList()));
        return ResponseEntity.ok(restModelMapper.map(user, UserDTO.class));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId()).orElseThrow(() -> new UserNotFoundException(userPrincipal.getId()));
        UserDTO userDTO = restModelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{userId}/projects")
    public ResponseEntity<ProjectsResponse> getCurrentlyAssignedProjectsOfUser(@PathVariable("userId") Integer userId, @CurrentUser UserPrincipal userPrincipal) {
        ProjectsResponse projectsResponse = getCurrentlyAssignedProjects(userId);
        return ResponseEntity.ok(projectsResponse);
    }

    @GetMapping("/me/projects")
    public ResponseEntity<ProjectsResponse> getMyCurrentlyAssignedProjects(@CurrentUser UserPrincipal userPrincipal) {
        ProjectsResponse projectsResponse = getCurrentlyAssignedProjects(userPrincipal.getId());
        return ResponseEntity.ok(projectsResponse);
    }

    private ProjectsResponse getCurrentlyAssignedProjects(Integer userId) {
        List<Project> projects = projectService.findAllCurrentlyAssignedProjectsByUserId(userId);
        ProjectsResponse projectsResponse = new ProjectsResponse();
        projectsResponse.setProjects(projects.stream().map(p -> restModelMapper.map(p, ProjectDTO.class)).collect(Collectors.toList()));
        return projectsResponse;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<UserRolesResponse> getAllUserRoles(@CurrentUser UserPrincipal userPrincipal) {
        List<UserRole> userRoles = userService.findAllUserRoles();
        UserRolesResponse userRolesResponse = new UserRolesResponse();
        userRolesResponse.setUserRoles(userRoles.stream().map(r -> restModelMapper.map(r, UserRoleDTO.class)).collect(Collectors.toList()));
        return ResponseEntity.ok(userRolesResponse);
    }
}
