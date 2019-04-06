package cz.cvut.fit.timetracking.data.api;

import cz.cvut.fit.timetracking.configuration.DataAccessApiTestsConfiguration;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDataAccessApiTests extends DataAccessApiTestsConfiguration {

    @Autowired
    private DataAccessApi dataAccessApi;

    @Test
    @Transactional
    public void createUser() {
        UserDTO userDTO = getUser();
        UserDTO createdUser = dataAccessApi.createOrUpdateUser(userDTO);
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getName()).isEqualTo(userDTO.getName());
        assertThat(createdUser.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(createdUser.getSurname()).isEqualTo(userDTO.getSurname());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUserIsNull_createUser_expectIllegalArgumentException() {
        dataAccessApi.createOrUpdateUser(null);
    }

    @Test
    @Transactional
    public void deleteUser() {
        UserDTO createdUser = dataAccessApi.createOrUpdateUser(getUser());
        dataAccessApi.deleteUserById(createdUser.getId());
        assertThat(dataAccessApi.findUserById(createdUser.getId())).isEmpty();
    }

    private UserDTO getUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setEmail("tm@tmp.com");
        userDTO.setSurname("Testovic");
        return userDTO;
    }
}
