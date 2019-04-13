package cz.cvut.fit.timetracking.data.api;

import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface DataAccessApi {

    //user
    UserDTO createOrUpdateUser(UserDTO user);
    Optional<UserDTO> findUserById(Integer id);
    void deleteUserById(Integer id);

    //project
    ProjectDTO createOrUpdateProject(ProjectDTO project);
    List<ProjectDTO> findAllProjects();
    Optional<ProjectDTO> findProjectById(Integer id);
    void deleteProjectById(Integer id);
}
