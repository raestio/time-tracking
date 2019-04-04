package cz.cvut.fit.timetracking.data.api;

import cz.cvut.fit.timetracking.data.api.dto.User;

import java.util.Optional;

public interface DataAccessApi {
    Optional<User> findUserById(Integer id);
}
