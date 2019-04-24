package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.entity.WorkRecord;
import cz.cvut.fit.timetracking.data.repository.WorkRecordRepository;
import cz.cvut.fit.timetracking.data.service.WorkRecordDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkRecordDataServiceImpl implements WorkRecordDataService {

    @Autowired
    private WorkRecordRepository workRecordRepository;

    @Override
    public boolean recordTimesOverlapsWithOtherRecords(LocalDateTime from, LocalDateTime to, Integer userId) {
        List<WorkRecord> workRecords =  workRecordRepository.findRecordsThatOverlap(from, to, userId);
        return !workRecords.isEmpty();
    }

}
