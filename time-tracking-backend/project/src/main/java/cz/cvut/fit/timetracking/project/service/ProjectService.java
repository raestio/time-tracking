package cz.cvut.fit.timetracking.project.service;

import cz.cvut.fit.timetracking.project.dto.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Project createOrUpdate(Project project);
    List<Project> findAll();
    Optional<Project> findById(Integer id);
    void deleteById(Integer id);
}
