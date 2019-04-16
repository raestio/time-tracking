package cz.cvut.fit.timetracking.data.utils;

import cz.cvut.fit.timetracking.data.api.dto.AuthProvider;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.entity.UserRole;
import cz.cvut.fit.timetracking.data.entity.enums.AuthProviderEnum;
import cz.cvut.fit.timetracking.data.entity.enums.UserRoleName;

import java.util.ArrayList;
import java.util.List;

public class UserTestUtils {

    public static UserDTO getUserDTO() {
        UserDTO user = new UserDTO();
        user.setName("Test");
        user.setSurname("Novy");
        user.setAuthProvider(AuthProvider.GOOGLE);
        user.setEmail("tmp@test__0.com");
        return user;
    }

    public static User getUser() {
        User user = new User();
        user.setName("Test");
        user.setSurname("Novy2");
        user.setAuthProvider(AuthProviderEnum.GOOGLE);
        user.setEmail("tmp@test__1.com");
        return user;
    }

    public static List<UserRole> getUserRoles() {
        List<UserRole> roles = new ArrayList<>();
        UserRole userRole1 = new UserRole();
        userRole1.setName(UserRoleName.USER);
        UserRole userRole2 = new UserRole();
        userRole2.setName(UserRoleName.ADMIN);
        roles.add(userRole1);
        roles.add(userRole2);
        return roles;
    }
}
