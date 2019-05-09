package cz.cvut.fit.timetracking.rest.utils;

import cz.cvut.fit.timetracking.rest.dto.user.UserRoleName;
import cz.cvut.fit.timetracking.rest.dto.user.request.UpdateUserRolesRequest;

import java.util.List;

public class RequestCreationUtils {

    public static UpdateUserRolesRequest userAndAdmin() {
        UpdateUserRolesRequest updateUserRolesRequest = new UpdateUserRolesRequest();
        updateUserRolesRequest.setUserRoles(List.of(UserRoleName.USER, UserRoleName.ADMIN));
        return updateUserRolesRequest;
    }

    public static UpdateUserRolesRequest adminOnly() {
        UpdateUserRolesRequest updateUserRolesRequest = new UpdateUserRolesRequest();
        updateUserRolesRequest.setUserRoles(List.of(UserRoleName.ADMIN));
        return updateUserRolesRequest;
    }

    public static UpdateUserRolesRequest userOnly() {
        UpdateUserRolesRequest updateUserRolesRequest = new UpdateUserRolesRequest();
        updateUserRolesRequest.setUserRoles(List.of(UserRoleName.USER));
        return updateUserRolesRequest;
    }
}
