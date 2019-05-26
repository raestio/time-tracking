package cz.cvut.fit.timetracking.project.service;

import cz.cvut.fit.timetracking.project.dto.WorkType;

import java.util.List;
import java.util.Optional;

public interface WorkTypeService {
    WorkType create(String name, String description);
    List<WorkType> findAll();
    Optional<WorkType> findById(Integer workTypeId);
    WorkType update(Integer workTypeId, String name, String description);
    void deleteById(Integer workTypeId);
}
