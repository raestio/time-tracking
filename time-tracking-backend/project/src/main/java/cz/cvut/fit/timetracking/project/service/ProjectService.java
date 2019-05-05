package cz.cvut.fit.timetracking.project.service;

import cz.cvut.fit.timetracking.project.dto.Project;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project create(String name, String description, LocalDate start, LocalDate end);
    Project update(int projectId, String name, String description, LocalDate start, LocalDate end);

    List<Project> findAll();
    Optional<Project> findById(Integer id);
    void deleteById(Integer id);
}
