package cz.cvut.fit.timetracking.project.service;

import cz.cvut.fit.timetracking.project.dto.Project;
import cz.cvut.fit.timetracking.project.dto.ProjectAssignment;
import cz.cvut.fit.timetracking.project.dto.ProjectRole;
import cz.cvut.fit.timetracking.project.dto.ProjectRoleName;
import cz.cvut.fit.timetracking.project.dto.WorkType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project create(String name, String description, LocalDate start, LocalDate end, List<WorkType> workTypes);
    Project update(Integer projectId, String name, String description, LocalDate start, LocalDate end, List<WorkType> workTypes);
    List<Project> findAll();
    Optional<Project> findById(Integer id);
    List<Project> findAllCurrentlyAssignedProjectsByUserId(Integer userId);
    void deleteById(Integer id);
    Optional<ProjectRole> findProjectRoleByName(ProjectRoleName projectRoleName);
    List<ProjectRole> findAllProjectRoles();
    boolean isUserAssignedToProject(Integer userId, Integer projectId);
}
