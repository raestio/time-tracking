package cz.cvut.fit.timetracking.user;

import cz.cvut.fit.timetracking.configuration.UserTestsConfiguration;
import cz.cvut.fit.timetracking.user.dto.AuthProvider;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.exception.UpdateUserException;
import cz.cvut.fit.timetracking.user.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "/sql_initialization_test_scripts/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserServiceUpdateTests extends UserTestsConfiguration {
    private String email;

    @Autowired
    private UserService userService;

    @Before
    public void init() {
        email = "tmp@agent007.com";
        createUser();
    }

    @After
    public void cleanUp() {
        userService.deleteById(userService.findByEmail(email).get().getId());
    }

    @Test(expected = UpdateUserException.class)
    public void test_whenRolesAreRemoved_throwException() {
        User user = userService.findByEmail(email).get();
        user.getUserRoles().clear();
        userService.updateUserRoles(user.getId(), user.getUserRoles().stream().map(u -> u.getName()).collect(Collectors.toList()));
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
        User updatedUser = userService.updateUserRoles(user.getId(), user.getUserRoles().stream().map(u -> u.getName()).collect(Collectors.toList()));
        assertThat(updatedUser.getUserRoles()).hasSize(1);
        assertThat(updatedUser.getUserRoles().get(0).getName()).isEqualTo(cz.cvut.fit.timetracking.user.dto.UserRoleName.USER);
    }

    @Test(expected = UpdateUserException.class)
    public void test_whenUserRoleIsRemoved_throwException() {
        findAndAddAdminRole();
        User user = userService.findByEmail(email).get();
        user.setUserRoles(user.getUserRoles().stream().filter(r -> !r.getName().equals(cz.cvut.fit.timetracking.user.dto.UserRoleName.USER)).collect(Collectors.toList()));
        userService.updateUserRoles(user.getId(), user.getUserRoles().stream().map(u -> u.getName()).collect(Collectors.toList()));
    }

    private void findAndAddAdminRole() {
        User user = userService.findByEmail(email).get();
        cz.cvut.fit.timetracking.user.dto.UserRole userRole = new cz.cvut.fit.timetracking.user.dto.UserRole();
        userRole.setName(cz.cvut.fit.timetracking.user.dto.UserRoleName.ADMIN);
        user.getUserRoles().add(userRole);
        User updatedUser = userService.updateUserRoles(user.getId(), user.getUserRoles().stream().map(u -> u.getName()).collect(Collectors.toList()));
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

}
