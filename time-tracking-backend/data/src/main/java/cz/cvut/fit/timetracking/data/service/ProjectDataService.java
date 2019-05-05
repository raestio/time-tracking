package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTOLight;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectDataService {
    ProjectDTO createOrUpdate(ProjectDTO project);
    List<ProjectDTO> findAll();
    Optional<ProjectDTO> findById(Integer id);
    List<ProjectDTO> findAllAssignedProjectsWhereValidTimeOverlapsByUserId(LocalDate date, Integer userId);
    void deleteById(Integer id);

    List<ProjectAssignmentDTO> findProjectAssignmentsByProjectId(Integer projectId);
    Optional<ProjectAssignmentDTO> findProjectAssignmentById(Integer id);
    List<ProjectAssignmentDTO> findProjectAssignmentsByProjectIdAndUserId(Integer projectId, Integer userId);
    ProjectAssignmentDTOLight createOrUpdateProjectAssignment(ProjectAssignmentDTOLight projectAssignmentDTOLight);

    void deleteProjectAssignmentById(Integer id);
}
