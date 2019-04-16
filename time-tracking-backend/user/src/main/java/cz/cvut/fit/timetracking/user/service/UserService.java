package cz.cvut.fit.timetracking.user.service;

import cz.cvut.fit.timetracking.user.dto.AuthProvider;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.dto.UserRole;
import cz.cvut.fit.timetracking.user.dto.UserRoleName;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(String name, String surname, String email, AuthProvider authProvider);
    User create(String name, String surname, String email, AuthProvider authProvider, String pictureUrl);

    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);

    User update(User user);

    void deleteById(Integer id);
}
