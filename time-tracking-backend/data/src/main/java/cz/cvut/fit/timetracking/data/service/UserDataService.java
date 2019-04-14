package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.UserDTO;

import java.util.Optional;

/**
 * User data access service
 */
public interface UserDataService {
    Optional<UserDTO> findById(Integer id);
    Optional<UserDTO> findByEmail(String email);
    UserDTO createOrUpdate(UserDTO user);
    void deleteById(Integer id);
}
