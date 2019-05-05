package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.rest.dto.user.UpdateUserRequest;
import cz.cvut.fit.timetracking.rest.dto.user.UserDTO;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import cz.cvut.fit.timetracking.security.CurrentUser;
import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import cz.cvut.fit.timetracking.user.dto.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestModelMapper restModelMapper;


    @ApiOperation(value = "Get an user by ID", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User with given ID not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@ApiParam(value = "User ID") @PathVariable("id") Integer id) {
        Optional<User> user = userService.findById(id);
        ResponseEntity<UserDTO> response = user.map(u -> ResponseEntity.ok(restModelMapper.map(u, UserDTO.class))).orElseGet(() -> ResponseEntity.notFound().build());
        return response;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        //TODO
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userService.findById(userPrincipal.getId()).orElseThrow(() -> new UserNotFoundException(userPrincipal.getId()));
        UserDTO userDTO = restModelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{userID}/projects")
    public ResponseEntity<UserDTO> getProjectsOfUser(@PathVariable("userId") Integer userId, @CurrentUser UserPrincipal userPrincipal) {
        //todo
    }

    @GetMapping("/me/projects")
    public ResponseEntity<UserDTO> getMyAssignedProjects(@CurrentUser UserPrincipal userPrincipal) {
        //todo
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<UserDTO> getUserRoles(@CurrentUser UserPrincipal userPrincipal) {
        //TODO
    }
}
