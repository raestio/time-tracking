package cz.cvut.fit.timetracking.rest.utils;

import cz.cvut.fit.timetracking.project.dto.ProjectRoleName;
import cz.cvut.fit.timetracking.rest.dto.project.WorkTypeDTO;
import cz.cvut.fit.timetracking.rest.dto.project.request.CreateOrUpdateProjectAssignmentRequest;
import cz.cvut.fit.timetracking.rest.dto.project.request.CreateOrUpdateProjectRequest;
import cz.cvut.fit.timetracking.rest.dto.project.request.CreateOrUpdateWorkTypeRequest;
import cz.cvut.fit.timetracking.rest.dto.user.UserRoleName;
import cz.cvut.fit.timetracking.rest.dto.user.request.UpdateUserRolesRequest;
import cz.cvut.fit.timetracking.rest.dto.workrecord.request.CreateOrUpdateWorkRecordRequest;

import java.time.LocalDate;
import java.util.Arrays;
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

    public static CreateOrUpdateWorkTypeRequest workType() {
        CreateOrUpdateWorkTypeRequest createOrUpdateWorkTypeRequest = new CreateOrUpdateWorkTypeRequest();
        createOrUpdateWorkTypeRequest.setName("test work type");
        createOrUpdateWorkTypeRequest.setDescription("ahoj");
        return createOrUpdateWorkTypeRequest;
    }

    public static CreateOrUpdateWorkTypeRequest workType2() {
        CreateOrUpdateWorkTypeRequest createOrUpdateWorkTypeRequest = new CreateOrUpdateWorkTypeRequest();
        createOrUpdateWorkTypeRequest.setName("vyvoj");
        return createOrUpdateWorkTypeRequest;
    }

    public static WorkTypeDTO existingWorkType() {
        WorkTypeDTO workTypeDTO = new WorkTypeDTO();
        workTypeDTO.setId(-1);
        workTypeDTO.setName("vyvoj");
        return workTypeDTO;
    }

    public static CreateOrUpdateProjectAssignmentRequest projectAssignmentWithoutRole(int projectId) {
        CreateOrUpdateProjectAssignmentRequest request = new CreateOrUpdateProjectAssignmentRequest();
        request.setProjectId(projectId);
        request.setUserId(-1);
        request.setValidFrom(LocalDate.parse("2019-05-01"));
        return request;
    }

    public static CreateOrUpdateProjectAssignmentRequest projectAssignmentMember(int projectId) {
        CreateOrUpdateProjectAssignmentRequest request = projectAssignmentWithoutRole(projectId);
        request.getProjectRoleNames().add(ProjectRoleName.MEMBER);
        return request;
    }

    public static CreateOrUpdateProjectAssignmentRequest projectAssignmentMember2(int projectId) {
        CreateOrUpdateProjectAssignmentRequest request = projectAssignmentWithoutRole(projectId);
        request.getProjectRoleNames().add(ProjectRoleName.MEMBER);
        request.setValidTo(request.getValidFrom());
        return request;
    }

    public static CreateOrUpdateProjectAssignmentRequest projectAssignmentProjectManager(int projectId) {
        CreateOrUpdateProjectAssignmentRequest request = projectAssignmentWithoutRole(projectId);
        request.getProjectRoleNames().add(ProjectRoleName.PROJECT_MANAGER);
        return request;
    }

    public static CreateOrUpdateProjectAssignmentRequest projectAssignmentProjectManagerAndMember(int projectId) {
        CreateOrUpdateProjectAssignmentRequest request = projectAssignmentProjectManager(projectId);
        request.getProjectRoleNames().add(ProjectRoleName.MEMBER);
        return request;
    }

    public static CreateOrUpdateWorkRecordRequest workRecord(Integer userId, Integer projectId) {
        CreateOrUpdateWorkRecordRequest createOrUpdateWorkRecordRequest = new CreateOrUpdateWorkRecordRequest();
        createOrUpdateWorkRecordRequest.setUserId(userId);
        createOrUpdateWorkRecordRequest.setProjectId(projectId);
        createOrUpdateWorkRecordRequest.setWorkTypeId(-1);
        createOrUpdateWorkRecordRequest.setDateFrom(LocalDate.parse("2019-05-01").atStartOfDay());
        createOrUpdateWorkRecordRequest.setDateTo(LocalDate.parse("2019-05-01").atStartOfDay().plusHours(8));
        createOrUpdateWorkRecordRequest.setDescription("popis");
        return createOrUpdateWorkRecordRequest;
    }

    public static Object workRecord2(Integer userId, Integer projectId) {
        CreateOrUpdateWorkRecordRequest createOrUpdateWorkRecordRequest = new CreateOrUpdateWorkRecordRequest();
        createOrUpdateWorkRecordRequest.setUserId(userId);
        createOrUpdateWorkRecordRequest.setProjectId(projectId);
        createOrUpdateWorkRecordRequest.setWorkTypeId(-1);
        createOrUpdateWorkRecordRequest.setDateFrom(LocalDate.parse("2019-05-29").atStartOfDay());
        createOrUpdateWorkRecordRequest.setDateTo(LocalDate.parse("2019-05-29").atStartOfDay().plusHours(8));
        createOrUpdateWorkRecordRequest.setDescription("popis 2");
        return createOrUpdateWorkRecordRequest;
    }
}
