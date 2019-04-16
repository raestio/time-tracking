package cz.cvut.fit.timetracking.user;

import cz.cvut.fit.timetracking.configuration.UserTestsConfiguration;
import cz.cvut.fit.timetracking.data.entity.UserRole;
import cz.cvut.fit.timetracking.data.repository.UserRoleRepository;
import cz.cvut.fit.timetracking.user.dto.AuthProvider;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.dto.UserRoleName;
import cz.cvut.fit.timetracking.user.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceCreateTests extends UserTestsConfiguration {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Before
    public void init() {
        UserRole userRole = new UserRole();
        userRole.setName(cz.cvut.fit.timetracking.data.entity.enums.UserRoleName.USER);
        userRoleRepository.save(userRole);
    }

    @After
    public void cleanUp() {
        userRoleRepository.deleteAll();
    }

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
