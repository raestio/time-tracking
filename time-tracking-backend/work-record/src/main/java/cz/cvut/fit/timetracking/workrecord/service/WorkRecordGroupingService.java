package cz.cvut.fit.timetracking.workrecord.service;

import cz.cvut.fit.timetracking.workrecord.dto.Project;
import cz.cvut.fit.timetracking.workrecord.dto.User;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.dto.WorkType;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface WorkRecordGroupingService {
    Map<WorkType, List<WorkRecord>> groupByWorkType(List<WorkRecord> workRecords);
    Map<Project, List<WorkRecord>> groupByProjects(List<WorkRecord> workRecords);
    Map<LocalDate, List<WorkRecord>> groupByDate(List<WorkRecord> workRecords);
    Map<YearMonth, List<WorkRecord>> groupByYearMonth(List<WorkRecord> workRecords);
    Map<Month, List<WorkRecord>> groupByMonth(List<WorkRecord> workRecords);
    Map<Year, List<WorkRecord>> groupByYear(List<WorkRecord> workRecords);

    Map<User, List<WorkRecord>> groupByUser(List<WorkRecord> workRecords);
}
