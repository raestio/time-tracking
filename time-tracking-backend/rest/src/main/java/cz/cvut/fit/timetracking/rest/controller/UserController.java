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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestModelMapper restModelMapper;

    /*
    @ApiOperation(value = "Get an user by ID", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "User with given ID not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@ApiParam(value = "User ID") @PathVariable("id") Integer id) {
        Optional<User> user = userService.findById(id);
        ResponseEntity<UserDTO> response = user.map(u -> ResponseEntity.ok(restModelMapper.map(u, UserDTO.class))).orElseGet(() -> ResponseEntity.notFound().build());
        return response;
    }*/

    /*
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        Optional<User> user = userService.findById(id);
        ResponseEntity<UserDTO> response = user.map(u -> {
            u.setName(updateUserRequest.getName());
            u.setEmail(updateUserRequest.getEmail());
            u.setSurname(updateUserRequest.getSurname());
            User updatedUser = userService.createOrUpdate(u);
            return ResponseEntity.ok(restModelMapper.map(updatedUser, UserDTO.class));
        }).orElseGet(() -> ResponseEntity.notFound().build());
        return response;
    }
    */

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getId())
                .orElseThrow(() -> new UserNotFoundException(userPrincipal.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
