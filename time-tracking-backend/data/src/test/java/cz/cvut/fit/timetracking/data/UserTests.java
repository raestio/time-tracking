package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.configuration.DataTestsConfiguration;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.repository.UserRepository;
import cz.cvut.fit.timetracking.data.service.UserDataService;
import cz.cvut.fit.timetracking.data.utils.DataTestUtils;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests extends DataTestsConfiguration {

    @Autowired
    private UserDataService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        UserDTO user = DataTestUtils.getUserDTO();
        user = userService.createOrUpdate(user);
        assertThat(user.getId()).isNotNull();
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void testUpdateUser() {
        UserDTO user = userService.createOrUpdate(DataTestUtils.getUserDTO());
        user.setName("Rob");
        UserDTO updatedUser = userService.createOrUpdate(user);
        assertThat(userService.findById(updatedUser.getId()).get().getName()).isEqualTo("Rob");
    }

    @Test
    public void testFindUserById() {
        UserDTO user = userService.createOrUpdate(DataTestUtils.getUserDTO());
        assertThat(user.getEmail()).isEqualTo(userService.findById(user.getId()).get().getEmail());
    }

    @Test
    public void testFindUserByEmail() {
        UserDTO user = userService.createOrUpdate(DataTestUtils.getUserDTO());
        assertThat(user.getId()).isEqualTo(userService.findByEmail(user.getEmail()).get().getId());
    }

    @Test
    public void testDeleteUserById() {
        UserDTO user = userService.createOrUpdate(DataTestUtils.getUserDTO());
        userService.deleteById(user.getId());
        assertThat(userService.findById(user.getId())).isEmpty();
    }
}
