package cz.cvut.fit.timetracking.data.api;

import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTOLight;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleName;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DataAccessApi {

    //user
    UserDTO createOrUpdateUser(UserDTO user);
    Optional<UserDTO> findUserById(Integer id);
    Optional<UserDTO> findUserByEmail(String email);
    void deleteUserById(Integer id);

    //user roles
    List<UserRoleDTO> findAllUserRolesByUserId(Integer id);
    List<UserRoleDTO> findUserRolesByNameIn(List<UserRoleName> roleNames);

    //project
    ProjectDTO createOrUpdateProject(ProjectDTO project);
    List<ProjectDTO> findAllProjects();
    Optional<ProjectDTO> findProjectById(Integer id);
    void deleteProjectById(Integer id);
    List<ProjectDTO> findAllAssignedProjectsWhereValidTimeOverlapsByUserId(LocalDate date, Integer userId);

    //project roles
    List<ProjectRoleDTO> findAllProjectRoles();
    Optional<ProjectRoleDTO> findProjectRoleByName(ProjectRoleName projectRoleName);
    List<ProjectRoleDTO> findAllProjectRolesIn(List<ProjectRoleName> roleNames);

    //project assignments
    List<ProjectAssignmentDTO> findProjectAssignmentsByProjectId(Integer projectId);
    Optional<ProjectAssignmentDTO> findProjectAssignmentById(Integer id);
    void deleteProjectAssignmentById(Integer id);
    List<ProjectAssignmentDTO> findProjectAssignmentsByProjectIdAndUserId(Integer projectId, Integer userId);
    ProjectAssignmentDTOLight createOrUpdateProjectAssignment(ProjectAssignmentDTOLight projectAssignmentDTO);

    //work records
    boolean workRecordTimesOverlapWithOtherUserRecords(LocalDateTime from, LocalDateTime to, Integer userId);
    WorkRecordDTOLight createOrUpdateWorkRecord(WorkRecordDTOLight workRecordDTOLight);
    WorkRecordDTO createOrUpdateWorkRecord(WorkRecordDTO workRecordDTO);
    void deleteWorkRecordById(Integer id);
    Optional<WorkRecordDTO> findWorkRecordById(Integer id);
    List<WorkRecordDTO> findAllWorkRecordsBetween(LocalDateTime fromInclusive, LocalDateTime toExclusive);
    List<WorkRecordDTO> findAllWorkRecordsBetweenByUserId(LocalDateTime fromInclusive, LocalDateTime toExclusive, Integer userId);
}
