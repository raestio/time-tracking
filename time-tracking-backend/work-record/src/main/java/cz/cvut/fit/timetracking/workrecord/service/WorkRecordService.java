package cz.cvut.fit.timetracking.workrecord.service;

import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkRecordService {
    WorkRecord create(LocalDateTime from, LocalDateTime to, String description, int projectId, int workTypeId, int userId);

    WorkRecord update(int workRecordId, LocalDateTime from, LocalDateTime to, String description, int projectId, int workTypeId);

    Optional<WorkRecord> findById(int id);

    void deleteById(int id);
}
