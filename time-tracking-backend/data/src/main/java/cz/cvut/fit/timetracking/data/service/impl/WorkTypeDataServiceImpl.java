package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.api.dto.WorkTypeDTO;
import cz.cvut.fit.timetracking.data.entity.WorkType;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.repository.WorkTypeRepository;
import cz.cvut.fit.timetracking.data.service.WorkTypeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkTypeDataServiceImpl implements WorkTypeDataService {

    @Autowired
    private WorkTypeRepository workTypeRepository;

    @Autowired
    private DataModelMapper dataModelMapper;

    @Override
    public List<WorkTypeDTO> findAll() {
        List<WorkType> workTypes = workTypeRepository.findAll();
        List<WorkTypeDTO> workTypeDTOs = workTypes.stream().map(this::map).collect(Collectors.toList());
        return workTypeDTOs;
    }

    @Override
    public WorkTypeDTO createOrUpdate(WorkTypeDTO workTypeDTO) {
        WorkType workType = dataModelMapper.map(workTypeDTO, WorkType.class);
        workType = workTypeRepository.save(workType);
        WorkTypeDTO result = map(workType);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        workTypeRepository.deleteById(id);
    }

    @Override
    public Optional<WorkTypeDTO> findById(Integer workTypeId) {
        return workTypeRepository.findById(workTypeId).map(this::map);
    }

    private WorkTypeDTO map(WorkType workType) {
        return dataModelMapper.map(workType, WorkTypeDTO.class);
    }
}
