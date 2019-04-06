package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.entity.User;

import java.util.Optional;

/**
 * User data access service
 */
public interface UserService {
    Optional<User> findById(Integer id);
    User createOrUpdateUser(User user);
}
