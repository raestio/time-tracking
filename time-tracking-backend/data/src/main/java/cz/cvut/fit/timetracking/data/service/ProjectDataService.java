package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectDataService {
    ProjectDTO createOrUpdate(ProjectDTO project);
    List<ProjectDTO> findAll();
    Optional<ProjectDTO> findById(Integer id);
    void deleteById(Integer id);

    List<ProjectAssignmentDTO> findProjectAssignmentsByProjectId(Integer projectId);
}
