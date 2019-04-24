package cz.cvut.fit.timetracking.data.api;

import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleName;

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

    //work records
    boolean workRecordTimesOverlapWithOtherUserRecords(LocalDateTime from, LocalDateTime to, Integer userId);
}
