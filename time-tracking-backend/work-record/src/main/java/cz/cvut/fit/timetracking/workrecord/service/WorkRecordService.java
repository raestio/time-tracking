package cz.cvut.fit.timetracking.workrecord.service;

import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;

import java.util.Optional;

public interface WorkRecordService {
    WorkRecord createOrUpdate(WorkRecord workRecord);
    Optional<WorkRecord> findById(Integer id);
    void deleteById(Integer id);
}
