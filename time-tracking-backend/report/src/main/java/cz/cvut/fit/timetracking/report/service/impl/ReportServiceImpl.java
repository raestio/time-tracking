package cz.cvut.fit.timetracking.report.service.impl;

import cz.cvut.fit.timetracking.report.component.ReportItemsHelper;
import cz.cvut.fit.timetracking.report.dto.DayReportItem;
import cz.cvut.fit.timetracking.report.dto.MonthReportItem;
import cz.cvut.fit.timetracking.report.dto.ProjectReportItem;
import cz.cvut.fit.timetracking.report.dto.UserReportItem;
import cz.cvut.fit.timetracking.report.dto.YearReportItem;
import cz.cvut.fit.timetracking.report.service.ReportService;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordGroupingService;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private WorkRecordService workRecordService;

    @Autowired
    private WorkRecordGroupingService workRecordGroupingService;

    @Autowired
    private ReportItemsHelper reportItemsHelper;

    @Override
    public List<DayReportItem> createDailyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId) {
        List<DayReportItem> dayReportItems = createReportsByTemplate(
                () -> findWorkRecordsBetween(fromInclusive, toExclusive, userId),
                (workRecords ->  workRecordGroupingService.groupByDate(workRecords)),
                ((day, workRecords) -> reportItemsHelper.createDayReportItem(day, workRecords))
        );
        return dayReportItems;
    }

    @Override
    public List<MonthReportItem> createMonthlyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId) {
        List<MonthReportItem> monthReportItems = createReportsByTemplate(
                () -> findWorkRecordsBetween(fromInclusive.withDayOfMonth(1), toExclusive.plusMonths(1).withDayOfMonth(1), userId),
                (workRecords -> workRecordGroupingService.groupByMonth(workRecords)),
                ((month, workRecords) -> reportItemsHelper.createMonthReportItem(month, workRecords))
        );
        return monthReportItems;
    }

    @Override
    public List<YearReportItem> createYearlyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId) {
        List<YearReportItem> yearReportItems = createReportsByTemplate(
                () -> findWorkRecordsBetween(fromInclusive.withDayOfYear(1), toExclusive.plusYears(1).withDayOfYear(1), userId),
                (workRecords -> workRecordGroupingService.groupByYear(workRecords)),
                ((year, workRecords) -> reportItemsHelper.createYearReportItem(year, workRecords))
        );
        return yearReportItems;
    }

    @Override
    public List<DayReportItem> createDailyReports(LocalDate fromInclusive, LocalDate toExclusive) {
        return createDailyReports(fromInclusive, toExclusive, null);
    }

    @Override
    public List<MonthReportItem> createMonthlyReports(LocalDate fromInclusive, LocalDate toExclusive) {
        return createMonthlyReports(fromInclusive, toExclusive, null);
    }

    @Override
    public List<YearReportItem> createYearlyReports(LocalDate fromInclusive, LocalDate toExclusive) {
        return createYearlyReports(fromInclusive, toExclusive, null);
    }

    @Override
    public List<UserReportItem> createUsersReports(LocalDate fromInclusive, LocalDate toExclusive) {
        List<UserReportItem> userReportItems = createReportsByTemplate(
                () -> findWorkRecordsBetween(fromInclusive, toExclusive),
                (workRecords -> workRecordGroupingService.groupByUser(workRecords)),
                ((user, workRecords) -> reportItemsHelper.createUserReportItem(user, workRecords))
        );
        return userReportItems;
    }

    @Override
    public List<ProjectReportItem> createProjectsReports(LocalDate fromInclusive, LocalDate toExclusive) {
        List<WorkRecord> workRecords = findWorkRecordsBetween(fromInclusive, toExclusive);
        return reportItemsHelper.createProjectReportItems(workRecords);
    }

    private List<WorkRecord> findWorkRecordsBetween(LocalDate fromInclusive, LocalDate toExclusive) {
        return findWorkRecordsBetween(fromInclusive, toExclusive, null);
    }

    private List<WorkRecord> findWorkRecordsBetween(LocalDate fromInclusive, LocalDate toExclusive, Integer userId) {
        if (userId != null) {
            return workRecordService.findAllBetweenByUserId(LocalDateTime.of(fromInclusive, LocalTime.MIN), LocalDateTime.of(toExclusive, LocalTime.MIN), userId);
        }
        return workRecordService.findAllBetween(LocalDateTime.of(fromInclusive, LocalTime.MIN), LocalDateTime.of(toExclusive, LocalTime.MIN));
    }

    private <REPORT_ITEM, GROUPED_BY_TYPE> List<REPORT_ITEM> createReportsByTemplate(Supplier<List<WorkRecord>> workRecordsSupplier, Function<List<WorkRecord>, Map<GROUPED_BY_TYPE, List<WorkRecord>>> groupedByTypeFunction, BiFunction<GROUPED_BY_TYPE, List<WorkRecord>, REPORT_ITEM> reportItemCreationFunction) {
        List<WorkRecord> workRecords = workRecordsSupplier.get();
        Map<GROUPED_BY_TYPE, List<WorkRecord>> groupedWorkRecords = groupedByTypeFunction.apply(workRecords);
        List<REPORT_ITEM> reportItems = groupedWorkRecords.entrySet().stream().map(e -> reportItemCreationFunction.apply(e.getKey(), e.getValue())).collect(Collectors.toList());
        return reportItems;
    }

}
