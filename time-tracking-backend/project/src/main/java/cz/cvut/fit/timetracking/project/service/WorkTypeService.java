package cz.cvut.fit.timetracking.project.service;

import cz.cvut.fit.timetracking.project.dto.WorkType;

import java.util.List;

public interface WorkTypeService {
    WorkType create(String name, String description);
    List<WorkType> findAll();
    WorkType update(Integer workTypeId, String name, String description);
    void deleteById(Integer workTypeId);
}
