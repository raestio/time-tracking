package cz.cvut.fit.timetracking.data.api;

import cz.cvut.fit.timetracking.configuration.DataAccessApiTestsConfiguration;
import cz.cvut.fit.timetracking.data.api.dto.AuthProvider;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDataAccessApiTests extends DataAccessApiTestsConfiguration {

    @Autowired
    private DataAccessApi dataAccessApi;

    @Test
    public void createUser() {
        UserDTO userDTO = getUser();
        UserDTO createdUser = dataAccessApi.createOrUpdateUser(userDTO);
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getName()).isEqualTo(userDTO.getName());
        assertThat(createdUser.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(createdUser.getSurname()).isEqualTo(userDTO.getSurname());
        assertThat(createdUser.getAuthProvider()).isEqualTo(AuthProvider.GOOGLE);
        dataAccessApi.deleteUserById(createdUser.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUserIsNull_createUser_expectIllegalArgumentException() {
        dataAccessApi.createOrUpdateUser(null);
    }

    @Test
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
        userDTO.setAuthProvider(AuthProvider.GOOGLE);
        return userDTO;
    }
}
