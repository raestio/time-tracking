package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.configuration.DataTestsConfiguration;
import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.service.UserDataService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests extends DataTestsConfiguration {

    @Autowired
    private UserDataService userService;

    @Test
    @Transactional
    public void testCreateUser() {
        User user = getUser();
        user = userService.createOrUpdate(user);
        assertThat(user.getId()).isNotNull();
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        User user = userService.createOrUpdate(getUser());
        user.setName("Rob");
        User updatedUser = userService.createOrUpdate(user);
        assertThat(userService.findById(updatedUser.getId()).get().getName()).isEqualTo("Rob");
    }

    @Test
    @Transactional
    public void testFindUserById() {
        User user = userService.createOrUpdate(getUser());
        assertThat(user.getEmail()).isEqualTo(userService.findById(user.getId()).get().getEmail());
    }

    @Test
    @Transactional
    public void testDeleteUserById() {
        User user = userService.createOrUpdate(getUser());
        userService.deleteById(user.getId());
        assertThat(userService.findById(user.getId())).isEmpty();
    }

    private User getUser() {
        User user = new User();
        user.setName("Test");
        user.setSurname("Novy");
        user.setEmail("tmp@test.com");
        return user;
    }
}
