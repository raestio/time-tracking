package cz.cvut.fit.timetracking.user.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleDTO;
import cz.cvut.fit.timetracking.user.dto.AuthProvider;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.dto.UserRole;
import cz.cvut.fit.timetracking.user.dto.UserRoleName;
import cz.cvut.fit.timetracking.user.exception.UpdateUserException;
import cz.cvut.fit.timetracking.user.exception.UserNotFoundException;
import cz.cvut.fit.timetracking.user.mapper.UserModelMapper;
import cz.cvut.fit.timetracking.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DataAccessApi dataAccessApi;

    @Autowired
    private UserModelMapper userModelMapper;

    @Override
    public Optional<User> findById(Integer id) {
        Optional<UserDTO> user = dataAccessApi.findUserById(id);
        Optional<User> result = user.map(this::map);
        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserDTO> user = dataAccessApi.findUserByEmail(email);
        Optional<User> result = user.map(this::map);
        return result;
    }

    @Override
    public List<User> findAll() {
        List<UserDTO> userDTOs = dataAccessApi.findAllUsers();
        List<User> users = userDTOs.stream().map(u -> userModelMapper.map(u, User.class)).collect(Collectors.toList());
        return users;
    }

    @Override
    public User updateUserRoles(Integer userId, List<UserRoleName> userRoles) {
        Assert.notNull(userId, "user id cannot be null");
        UserDTO user = dataAccessApi.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setUserRoles(findUserRolesByNameIn(userRoles));
        if (!hasUserRole(user)) {
            throw new UpdateUserException("User role USER can't be removed.");
        }
        UserDTO userDTO = dataAccessApi.createOrUpdateUser(user);
        return map(userDTO);
    }

    @Override
    public User create(String name, String surname, String email, AuthProvider authProvider) {
        return create(name, surname, email, authProvider, null);
    }

    @Override
    public User create(String name, String surname, String email, AuthProvider authProvider, String pictureUrl) {
        return create(name, surname, email, authProvider, pictureUrl, UserRoleName.USER);
    }

    @Override
    public User createOrUpdate(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setAuthProvider(cz.cvut.fit.timetracking.data.api.dto.AuthProvider.valueOf(user.getAuthProvider().toString()));
        userDTO.setPictureUrl(user.getPictureUrl());
        userDTO.setUserRoles(findUserRolesByNameIn(user.getUserRoles().stream().map(UserRole::getName).collect(Collectors.toList())));
        userDTO = dataAccessApi.createOrUpdateUser(userDTO);
        return userModelMapper.map(userDTO, User.class);
    }

    @Override
    public void deleteById(Integer id) {
        Assert.notNull(id, "user id cannot be null");
        Optional<User> user = findById(id);
        user.ifPresentOrElse(u -> dataAccessApi.deleteUserById(id), () -> {
            throw new UserNotFoundException(id);
        });
    }

    @Override
    public List<UserRole> findAllUserRoles() {
        List<UserRoleDTO> userRoleDTOs = dataAccessApi.findAllUserRoles();
        return userRoleDTOs.stream().map(this::map).collect(Collectors.toList());
    }

    private User create(String name, String surname, String email, AuthProvider authProvider, String pictureUrl, UserRoleName... userRoleNames) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setSurname(surname);
        userDTO.setEmail(email);
        userDTO.setAuthProvider(cz.cvut.fit.timetracking.data.api.dto.AuthProvider.valueOf(authProvider.toString()));
        userDTO.setPictureUrl(pictureUrl);
        userDTO.setUserRoles(findUserRolesByNameIn(List.of(userRoleNames)));
        userDTO = dataAccessApi.createOrUpdateUser(userDTO);
        User createdUser = map(userDTO);
        return createdUser;
    }

    private List<UserRoleDTO> findUserRolesByNameIn(List<UserRoleName> userRoleNames) {
        List<cz.cvut.fit.timetracking.data.api.dto.UserRoleName> roleNames = userRoleNames.stream().map(this::mapUserRoleName).collect(Collectors.toList());
        List<UserRoleDTO> userRoleDTOS = dataAccessApi.findUserRolesByNameIn(roleNames);
        Assert.isTrue(userRoleDTOS.size() == userRoleNames.size(), "All user roles must be persisted in DB: " + userRoleNames);
        return userRoleDTOS;
    }

    private UserRole map(UserRoleDTO r) {
        return userModelMapper.map(r, UserRole.class);
    }

    private User map(UserDTO u) {
        User user = userModelMapper.map(u, User.class);
        return user;
    }

    private cz.cvut.fit.timetracking.data.api.dto.UserRoleName mapUserRoleName(UserRoleName r) {
        return userModelMapper.map(r, cz.cvut.fit.timetracking.data.api.dto.UserRoleName.class);
    }

    private boolean hasUserRole(UserDTO user) {
        return user.getUserRoles().stream().anyMatch(r -> cz.cvut.fit.timetracking.data.api.dto.UserRoleName.USER.equals(r.getName()));
    }
}
