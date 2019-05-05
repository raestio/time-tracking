package cz.cvut.fit.timetracking.project.service;

import cz.cvut.fit.timetracking.project.dto.ProjectAssignment;
import cz.cvut.fit.timetracking.project.dto.ProjectRoleName;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectAssignmentService {

    List<ProjectAssignment> findProjectAssignmentsByProjectId(Integer projectId);
    Optional<ProjectAssignment> findProjectAssignmentById(Integer id);
    ProjectAssignment createProjectAssignment(Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo);
    ProjectAssignment createProjectAssignment(Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo, List<ProjectRoleName> projectRoleNames);
    ProjectAssignment updateProjectAssignment(Integer projectAssignmentId, Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo, List<ProjectRoleName> projectRoleNames);
    void deleteProjectAssignmentById(Integer id);
}
