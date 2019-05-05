package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.WorkTypeDTO;

import java.util.List;
import java.util.Optional;

public interface WorkTypeDataService {
    List<WorkTypeDTO> findAll();
    WorkTypeDTO createOrUpdate(WorkTypeDTO workTypeDTO);
    void deleteById(Integer id);
    Optional<WorkTypeDTO> findById(Integer workTypeId);
}
