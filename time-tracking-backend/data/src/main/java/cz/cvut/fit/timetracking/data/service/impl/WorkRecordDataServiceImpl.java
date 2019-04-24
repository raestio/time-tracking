package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;
import cz.cvut.fit.timetracking.data.entity.WorkRecord;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.repository.WorkRecordRepository;
import cz.cvut.fit.timetracking.data.service.WorkRecordDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkRecordDataServiceImpl implements WorkRecordDataService {

    @Autowired
    private WorkRecordRepository workRecordRepository;

    @Autowired
    private DataModelMapper dataModelMapper;

    @Override
    public boolean recordTimesOverlapsWithOtherRecords(LocalDateTime from, LocalDateTime to, Integer userId) {
        List<WorkRecord> workRecords =  workRecordRepository.findRecordsThatOverlap(from, to, userId);
        return !workRecords.isEmpty();
    }

    @Override
    public WorkRecordDTOLight createOrUpdate(WorkRecordDTOLight workRecordDTOLight) {
        WorkRecord workRecord = dataModelMapper.map(workRecordDTOLight, WorkRecord.class);
        workRecord = workRecordRepository.save(workRecord);
        return dataModelMapper.map(workRecord, WorkRecordDTOLight.class);
    }

    @Override
    public WorkRecordDTO createOrUpdate(WorkRecordDTO workRecordDTO) {
        WorkRecord workRecord = dataModelMapper.map(workRecordDTO, WorkRecord.class);
        workRecord = workRecordRepository.save(workRecord);
        return dataModelMapper.map(workRecord, WorkRecordDTO.class);
    }

    @Override
    public Optional<WorkRecordDTO> findById(Integer id) {
        Optional<WorkRecord> workRecordOptional = workRecordRepository.findById(id);
        return workRecordOptional.map(w -> dataModelMapper.map(w, WorkRecordDTO.class));
    }

    @Override
    public void deleteById(Integer id) {
        workRecordRepository.deleteById(id);
    }

}
