package cz.cvut.fit.timetracking.project.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.WorkTypeDTO;
import cz.cvut.fit.timetracking.project.dto.WorkType;
import cz.cvut.fit.timetracking.project.exception.WorkTypeNotFoundException;
import cz.cvut.fit.timetracking.project.mapper.ProjectModelMapper;
import cz.cvut.fit.timetracking.project.service.WorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkTypeServiceImpl implements WorkTypeService {

    @Autowired
    private DataAccessApi dataAccessApi;

    @Autowired
    private ProjectModelMapper projectModelMapper;

    @Override
    public WorkType create(String name, String description) {
        WorkType workType = createOrUpdate(null, name, description);
        return workType;
    }

    @Override
    public List<WorkType> findAll() {
        List<WorkTypeDTO> workTypeDTOs = dataAccessApi.findAllWorkTypes();
        return workTypeDTOs.stream().map(w -> projectModelMapper.map(w, WorkType.class)).collect(Collectors.toList());
    }

    @Override
    public WorkType update(Integer workTypeId, String name, String description) {
        dataAccessApi.findWorkTypeById(workTypeId).orElseThrow(() -> new WorkTypeNotFoundException(workTypeId));
        WorkType workType = createOrUpdate(workTypeId, name, description);
        return workType;
    }

    @Override
    public void deleteById(Integer workTypeId) {
        Assert.notNull(workTypeId, "work type id cannot be null");
        dataAccessApi.findWorkTypeById(workTypeId).orElseThrow(() -> new WorkTypeNotFoundException(workTypeId));
        dataAccessApi.deleteWorkTypeById(workTypeId);
    }

    private WorkType createOrUpdate(Integer workTypeId, String name, String description) {
        WorkTypeDTO workTypeDTO = new WorkTypeDTO();
        if (workTypeId != null) {
            workTypeDTO.setId(workTypeId);
        }
        workTypeDTO.setName(name);
        workTypeDTO.setDescription(description);
        workTypeDTO = dataAccessApi.createOrUpdateWorkType(workTypeDTO);
        return map(workTypeDTO);
    }

    private WorkType map(WorkTypeDTO workTypeDTO) {
        return projectModelMapper.map(workTypeDTO, WorkType.class);
    }
}
