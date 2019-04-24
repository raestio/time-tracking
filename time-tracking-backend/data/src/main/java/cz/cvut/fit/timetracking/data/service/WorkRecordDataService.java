package cz.cvut.fit.timetracking.data.service;

import java.time.LocalDateTime;

public interface WorkRecordDataService {
    boolean recordTimesOverlapsWithOtherRecords(LocalDateTime from, LocalDateTime to, Integer userId);
}
