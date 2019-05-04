package cz.cvut.fit.timetracking.workrecord.service.impl;

import cz.cvut.fit.timetracking.workrecord.dto.Project;
import cz.cvut.fit.timetracking.workrecord.dto.User;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.dto.WorkType;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordGroupingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkRecordGroupingServiceImpl implements WorkRecordGroupingService {

    @Override
    public Map<WorkType, List<WorkRecord>> groupByWorkType(List<WorkRecord> workRecords) {
        Map<WorkType, List<WorkRecord>> result = workRecords.stream().collect(Collectors.groupingBy(WorkRecord::getWorkType));
        return result;
    }

    @Override
    public Map<Project, List<WorkRecord>> groupByProjects(List<WorkRecord> workRecords) {
        Map<Project, List<WorkRecord>> result = workRecords.stream().collect(Collectors.groupingBy(WorkRecord::getProject));
        return result;
    }

    @Override
    public Map<LocalDate, List<WorkRecord>> groupByDate(List<WorkRecord> workRecords) {
        Map<LocalDate, List<WorkRecord>> result = workRecords.stream().collect(Collectors.groupingBy(workRecord -> workRecord.getDateFrom().toLocalDate()));
        return result;
    }

    @Override
    public Map<YearMonth, List<WorkRecord>> groupByYearMonth(List<WorkRecord> workRecords) {
        Map<YearMonth, List<WorkRecord>> result = workRecords.stream().collect(Collectors.groupingBy(workRecord -> YearMonth.of(workRecord.getDateFrom().toLocalDate().getYear(), workRecord.getDateFrom().toLocalDate().getMonth())));
        return result;
    }

    @Override
    public Map<Month, List<WorkRecord>> groupByMonth(List<WorkRecord> workRecords) {
        Map<Month, List<WorkRecord>> result = workRecords.stream().collect(Collectors.groupingBy(workRecord -> workRecord.getDateFrom().toLocalDate().getMonth()));
        return result;
    }

    @Override
    public Map<Year, List<WorkRecord>> groupByYear(List<WorkRecord> workRecords) {
        Map<Year, List<WorkRecord>> result = workRecords.stream().collect(Collectors.groupingBy(workRecord -> Year.of(workRecord.getDateFrom().toLocalDate().getYear())));
        return result;
    }

    @Override
    public Map<User, List<WorkRecord>> groupByUser(List<WorkRecord> workRecords) {
        Map<User, List<WorkRecord>> result = workRecords.stream().collect(Collectors.groupingBy(WorkRecord::getUser));
        return result;
    }
}
