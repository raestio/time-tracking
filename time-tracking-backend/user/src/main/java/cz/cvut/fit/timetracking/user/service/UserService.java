package cz.cvut.fit.timetracking.user.service;

import cz.cvut.fit.timetracking.user.dto.AuthProvider;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.dto.UserRole;
import cz.cvut.fit.timetracking.user.dto.UserRoleName;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //create
    User create(String name, String surname, String email, AuthProvider authProvider);
    User create(String name, String surname, String email, AuthProvider authProvider, String pictureUrl);
    User createOrUpdate(User user);

    //read
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    List<User> findAll();

    //update
    User updateUserRoles(Integer userId, List<UserRoleName> userRoles);

    //delete
    void deleteById(Integer id);

    List<UserRole> findAllUserRoles();
}
