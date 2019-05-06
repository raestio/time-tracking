package cz.cvut.fit.timetracking.workrecord.service;

import cz.cvut.fit.timetracking.workrecord.dto.WorkRecordConflictInfo;

import java.time.LocalDate;
import java.util.List;

public interface WorkRecordJiraService {
    boolean isUserAvailableInJira(Integer userId);
    List<WorkRecordConflictInfo> findWorkRecordsToImport(LocalDate fromInclusive, LocalDate toExclusive, Integer userId);
}
