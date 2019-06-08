package cz.cvut.fit.timetracking.report.service;

import cz.cvut.fit.timetracking.report.dto.DayReportItem;
import cz.cvut.fit.timetracking.report.dto.MonthReportItem;
import cz.cvut.fit.timetracking.report.dto.ProjectReportItem;
import cz.cvut.fit.timetracking.report.dto.UserReportItem;
import cz.cvut.fit.timetracking.report.dto.YearReportItem;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<DayReportItem> createDailyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId, Integer projectId);
    List<DayReportItem> createDailyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId);
    List<DayReportItem> createDailyReports(LocalDate fromInclusive, LocalDate toExclusive);

    List<MonthReportItem> createMonthlyReports(LocalDate from, LocalDate to, Integer userId, Integer projectId);
    List<MonthReportItem> createMonthlyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId);
    List<MonthReportItem> createMonthlyReports(LocalDate fromInclusive, LocalDate toExclusive);

    List<YearReportItem> createYearlyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId, Integer projectId);
    List<YearReportItem> createYearlyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId);
    List<YearReportItem> createYearlyReports(LocalDate fromInclusive, LocalDate toExclusive);

    List<UserReportItem> createUsersReports(LocalDate fromInclusive, LocalDate toExclusive);
    List<ProjectReportItem> createProjectsReports(LocalDate fromInclusive, LocalDate toExclusive);

    List<ProjectReportItem> createProjectsReports(LocalDate from, LocalDate to, Integer projectId);
}
