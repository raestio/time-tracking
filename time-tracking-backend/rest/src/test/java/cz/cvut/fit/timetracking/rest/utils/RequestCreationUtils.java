package cz.cvut.fit.timetracking.rest.utils;

import cz.cvut.fit.timetracking.rest.dto.project.WorkTypeDTO;
import cz.cvut.fit.timetracking.rest.dto.project.request.CreateOrUpdateProjectRequest;
import cz.cvut.fit.timetracking.rest.dto.user.UserRoleName;
import cz.cvut.fit.timetracking.rest.dto.user.request.UpdateUserRolesRequest;

import java.time.LocalDate;
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

    public static CreateOrUpdateProjectRequest project() {
        CreateOrUpdateProjectRequest createOrUpdateProjectRequest = new CreateOrUpdateProjectRequest();
        createOrUpdateProjectRequest.setName("idea");
        createOrUpdateProjectRequest.setDescription("popis");
        createOrUpdateProjectRequest.setStart(LocalDate.parse("2019-05-01"));
        createOrUpdateProjectRequest.setEnd(LocalDate.parse("2020-05-01"));
        createOrUpdateProjectRequest.setWorkTypes(List.of(existingWorkType()));
        return createOrUpdateProjectRequest;
    }

    public static WorkTypeDTO existingWorkType() {
        WorkTypeDTO workTypeDTO = new WorkTypeDTO();
        workTypeDTO.setId(-1);
        workTypeDTO.setName("vyvoj");
        return workTypeDTO;
    }
}
