package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserDTOLight;

import java.util.Optional;

/**
 * User data access service
 */
public interface UserDataService {
    Optional<UserDTO> findById(Integer id);
    Optional<UserDTO> findByEmail(String email);
    UserDTOLight createOrUpdate(UserDTOLight user);
    void deleteById(Integer id);
}
