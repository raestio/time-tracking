package cz.cvut.fit.timetracking.user.service;

import cz.cvut.fit.timetracking.user.dto.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Integer id);

    User createOrUpdate(User user);

    void deleteById(Integer id);
}
