package cz.cvut.fit.timetracking.user;

import cz.cvut.fit.timetracking.configuration.UserTestsConfiguration;
import cz.cvut.fit.timetracking.user.dto.AuthProvider;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.dto.UserRoleName;
import cz.cvut.fit.timetracking.user.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "/sql_initialization_test_scripts/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserServiceCreateTests extends UserTestsConfiguration {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateAndDeleteUser() {
        String name = "ahoj";
        String surname = "cau";
        String email = "tmp@test007.com";
        String pictureUrl = "tmpUrl";
        AuthProvider authProvider = AuthProvider.GOOGLE;
        User user = userService.create(name, surname, email, authProvider, pictureUrl);
        assertThat(user.getUserRoles().get(0).getName()).isEqualTo(UserRoleName.USER);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getSurname()).isEqualTo(surname);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPictureUrl()).isEqualTo(pictureUrl);
        assertThat(user.getAuthProvider()).isEqualTo(authProvider);
        userService.deleteById(user.getId());
        assertThat(userService.findById(user.getId())).isEmpty();
    }

}
