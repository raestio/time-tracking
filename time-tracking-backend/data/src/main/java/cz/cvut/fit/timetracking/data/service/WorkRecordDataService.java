package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WorkRecordDataService {
    boolean recordTimesOverlapsWithOtherRecords(LocalDateTime from, LocalDateTime to, Integer userId);
    WorkRecordDTOLight createOrUpdate(WorkRecordDTOLight workRecordDTOLight);
    WorkRecordDTO createOrUpdate(WorkRecordDTO workRecordDTO);
    Optional<WorkRecordDTO> findById(Integer id);
    void deleteById(Integer id);
}
