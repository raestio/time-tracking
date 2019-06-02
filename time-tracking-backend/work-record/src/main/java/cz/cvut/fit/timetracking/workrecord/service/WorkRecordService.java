package cz.cvut.fit.timetracking.workrecord.service;

import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.dto.input.WorkRecordInput;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkRecordService {
    WorkRecord create(LocalDateTime from, LocalDateTime to, String description, Integer projectId, Integer workTypeId, Integer userId);
    void create(List<WorkRecordInput> workRecordInputs);

    WorkRecord update(Integer workRecordId, LocalDateTime from, LocalDateTime to, String description, Integer projectId, Integer workTypeId);

    Optional<WorkRecord> findById(Integer id);
    List<WorkRecord> findAllBetween(LocalDateTime fromInclusive, LocalDateTime toExclusive);
    List<WorkRecord> findAllBetweenByUserId(LocalDateTime fromInclusive, LocalDateTime toExclusive, Integer userId);
    List<WorkRecord> findAllBetweenByUserIdAndProjectId(LocalDateTime fromInclusive, LocalDateTime toExclusive, Integer userId, Integer projectId);

    void deleteById(Integer id);

    boolean workRecordTimesOverlapWithOtherUserRecords(LocalDateTime from, LocalDateTime to, Integer userId);
}
