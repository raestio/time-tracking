package cz.cvut.fit.timetracking.data.api;

import cz.cvut.fit.timetracking.data.api.dto.UserDTO;

import java.util.Optional;

public interface DataAccessApi {
    Optional<UserDTO> findUserById(Integer id);
    UserDTO createOrUpdateUser(UserDTO user);
}
