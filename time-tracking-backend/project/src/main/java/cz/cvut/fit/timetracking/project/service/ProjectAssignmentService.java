package cz.cvut.fit.timetracking.project.service;

import cz.cvut.fit.timetracking.project.dto.ProjectAssignment;
import cz.cvut.fit.timetracking.project.dto.ProjectRoleName;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectAssignmentService {

    List<ProjectAssignment> findByProjectId(Integer projectId);
    Optional<ProjectAssignment> findById(Integer id);
    ProjectAssignment create(Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo);
    ProjectAssignment create(Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo, List<ProjectRoleName> projectRoleNames);
    ProjectAssignment update(Integer projectAssignmentId, Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo, List<ProjectRoleName> projectRoleNames);
    void deleteProjectAssignmentById(Integer id);
}
