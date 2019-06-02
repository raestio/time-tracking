package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkRecordDataService {
    WorkRecordDTOLight createOrUpdate(WorkRecordDTOLight workRecordDTOLight);
    WorkRecordDTO createOrUpdate(WorkRecordDTO workRecordDTO);

    Optional<WorkRecordDTO> findById(Integer id);
    List<WorkRecordDTO> findAllBetween(LocalDateTime fromInclusive, LocalDateTime toExclusive);
    List<WorkRecordDTO> findAllBetweenByUserId(LocalDateTime fromInclusive, LocalDateTime toExclusive, Integer userId);
    List<WorkRecordDTO> findAllWorkRecordsBetweenByUserIdAndProjectId(LocalDateTime fromInclusive, LocalDateTime toExclusive, Integer userId, Integer projectId);

    void deleteById(Integer id);
    boolean recordTimesOverlapsWithOtherRecords(LocalDateTime from, LocalDateTime to, Integer userId);
}
