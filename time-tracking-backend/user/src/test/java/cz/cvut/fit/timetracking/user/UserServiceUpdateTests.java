package cz.cvut.fit.timetracking.user;

import cz.cvut.fit.timetracking.configuration.UserTestsConfiguration;
import cz.cvut.fit.timetracking.data.entity.UserRole;
import cz.cvut.fit.timetracking.data.entity.enums.UserRoleName;
import cz.cvut.fit.timetracking.data.repository.UserRoleRepository;
import cz.cvut.fit.timetracking.user.dto.AuthProvider;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.exception.UpdateUserException;
import cz.cvut.fit.timetracking.user.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceUpdateTests extends UserTestsConfiguration {
    private String email;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Before
    public void init() {
        email = "tmp@agent007.com";
        createUserRole(UserRoleName.USER);
        createUserRole(UserRoleName.ADMIN);
        createUser();
    }

    @After
    public void cleanUp() {
        userService.deleteById(userService.findByEmail(email).get().getId());
        userRoleRepository.deleteAll();
    }

    @Test
    public void test_whenNameIsChanged_updateUser() {
        String name = "Nove";
        User user = userService.findByEmail(email).get();
        user.setName(name);
        user = userService.update(user);
        assertThat(user.getName()).isEqualTo(userService.findByEmail(email).get().getName());
        assertThat(user.getName()).isEqualTo(name);
    }

    @Test(expected = UpdateUserException.class)
    public void test_whenEmailIsChanged_throwException() {
        User user = userService.findByEmail(email).get();
        user.setEmail("new@tmp.com");
        userService.update(user);
    }

    @Test(expected = UpdateUserException.class)
    public void test_whenRolesAreRemoved_throwException() {
        User user = userService.findByEmail(email).get();
        user.getUserRoles().clear();
        userService.update(user);
    }

    @Test
    public void test_whenAdminRoleIsAdded_updateUser() {
        findAndAddAdminRole();
    }

    @Test
    public void test_whenAdminRoleIsRemoved_updateUser() {
        findAndAddAdminRole();
        User user = userService.findByEmail(email).get();
        user.setUserRoles(user.getUserRoles().stream().filter(r -> !r.getName().equals(cz.cvut.fit.timetracking.user.dto.UserRoleName.ADMIN)).collect(Collectors.toList()));
        User updatedUser = userService.update(user);
        assertThat(updatedUser.getUserRoles()).hasSize(1);
        assertThat(updatedUser.getUserRoles().get(0).getName()).isEqualTo(cz.cvut.fit.timetracking.user.dto.UserRoleName.USER);
    }

    @Test(expected = UpdateUserException.class)
    public void test_whenUserRoleIsRemoved_throwException() {
        findAndAddAdminRole();
        User user = userService.findByEmail(email).get();
        user.setUserRoles(user.getUserRoles().stream().filter(r -> !r.getName().equals(cz.cvut.fit.timetracking.user.dto.UserRoleName.USER)).collect(Collectors.toList()));
        userService.update(user);
    }

    private void findAndAddAdminRole() {
        User user = userService.findByEmail(email).get();
        cz.cvut.fit.timetracking.user.dto.UserRole userRole = new cz.cvut.fit.timetracking.user.dto.UserRole();
        userRole.setName(cz.cvut.fit.timetracking.user.dto.UserRoleName.ADMIN);
        user.getUserRoles().add(userRole);
        User updatedUser = userService.update(user);
        assertThat(updatedUser.getUserRoles()).hasSize(2);
        assertThat(updatedUser.getUserRoles().stream().map(cz.cvut.fit.timetracking.user.dto.UserRole::getName).collect(Collectors.toList())).contains(cz.cvut.fit.timetracking.user.dto.UserRoleName.USER, cz.cvut.fit.timetracking.user.dto.UserRoleName.ADMIN);
    }

    private void createUser() {
        String name = "ahoj";
        String surname = "cau";
        String pictureUrl = "tmpUrl";
        AuthProvider authProvider = AuthProvider.GOOGLE;
        userService.create(name, surname, email, authProvider, pictureUrl);
    }

    private void createUserRole(UserRoleName role) {
        UserRole userRole = new UserRole();
        userRole.setName(role);
        userRoleRepository.save(userRole);
    }
}
