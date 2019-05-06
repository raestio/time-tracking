package cz.cvut.fit.timetracking.report;

import cz.cvut.fit.timetracking.configuration.ReportTestsConfiguration;
import cz.cvut.fit.timetracking.jira.service.JiraWorklogService;
import cz.cvut.fit.timetracking.report.dto.DayReportItem;
import cz.cvut.fit.timetracking.report.dto.MonthReportItem;
import cz.cvut.fit.timetracking.report.dto.ProjectReportItem;
import cz.cvut.fit.timetracking.report.dto.UserReportItem;
import cz.cvut.fit.timetracking.report.dto.YearReportItem;
import cz.cvut.fit.timetracking.report.helper.ReportServiceTestsHelper;
import cz.cvut.fit.timetracking.report.service.ReportService;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordJiraService;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class ReportServiceTests extends ReportTestsConfiguration {

    @MockBean
    private WorkRecordService workRecordService;

    @Autowired
    private ReportService reportService;

    @MockBean
    private WorkRecordJiraService workRecordJiraService;

    private final LocalDate from = LocalDate.parse("2019-02-02");
    private final LocalDate to = LocalDate.parse("2019-02-27");

    @Before
    public void init() {
        given(workRecordService.findAllBetween(from.atStartOfDay(), to.atStartOfDay())).willReturn(ReportServiceTestsHelper.createWorkRecordsTestCase());
        given(workRecordService.findAllBetween(from.atStartOfDay(), to.atStartOfDay())).willReturn(ReportServiceTestsHelper.createWorkRecordsTestCase());
        given(workRecordService.findAllBetween(from.withDayOfMonth(1).atStartOfDay(), to.withDayOfMonth(1).atStartOfDay())).willReturn(ReportServiceTestsHelper.createWorkRecordsTestCase());
        given(workRecordService.findAllBetweenByUserId(from.withDayOfMonth(1).atStartOfDay(), to.withDayOfMonth(1).atStartOfDay(), 1)).willReturn(ReportServiceTestsHelper.createWorkRecordsTestCaseUserId1());
        given(workRecordService.findAllBetweenByUserId(from.withDayOfMonth(1).atStartOfDay(), to.withDayOfMonth(1).atStartOfDay(), 2)).willReturn(ReportServiceTestsHelper.createWorkRecordsTestCaseUserId2());
        given(workRecordService.findAllBetween(from.withDayOfYear(1).atStartOfDay(), to.withDayOfYear(1).atStartOfDay())).willReturn(ReportServiceTestsHelper.createWorkRecordsTestCase());
    }

    @Test
    public void testDailyReports() {
       List<DayReportItem> dayReportItems = reportService.createDailyReports(from, to);
        assertThat(dayReportItems).hasSize(3);

        assertThat(dayReportItems.get(0).getProjectReportItems()).hasSize(1);
        assertThat(dayReportItems.get(0).getProjectReportItems().get(0).getProject().getId()).isEqualTo(1);
        assertThat(dayReportItems.get(0).getProjectReportItems().get(0).getWorkReportItems().get(0).getMinutesSpent()).isEqualTo(90);

        assertThat(dayReportItems.get(1).getProjectReportItems()).hasSize(2);
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(dayReportItems.get(1).getProjectReportItems())).isEqualTo(270);

        assertThat(dayReportItems.get(2).getProjectReportItems()).hasSize(1);
        assertThat(dayReportItems.get(2).getProjectReportItems().get(0).getProject().getId()).isEqualTo(2);
        assertThat(dayReportItems.get(2).getProjectReportItems().get(0).getWorkReportItems().get(0).getMinutesSpent()).isEqualTo(90);
        assertThat(dayReportItems.get(2).getProjectReportItems().get(0).getWorkReportItems().get(0).getWorkType().getId()).isEqualTo(1);
    }

    @Test
    public void testMonthlyReports() {
        List<MonthReportItem> monthReportItems = reportService.createMonthlyReports(from, to);
        assertThat(monthReportItems).hasSize(2);
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(monthReportItems.get(0).getProjectReportItems())).isEqualTo(360);
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(monthReportItems.get(1).getProjectReportItems())).isEqualTo(90);
    }

    @Test
    public void testMonthlyReportsByUserId1() {
        List<MonthReportItem> monthReportItems = reportService.createMonthlyReports(from, to, 1);
        assertThat(monthReportItems).hasSize(1);
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(monthReportItems.get(0).getProjectReportItems())).isEqualTo(360);
        assertThat(monthReportItems.get(0).getProjectReportItems().size()).isEqualTo(2);
    }

    @Test
    public void testMonthlyReportsByUserId2() {
        List<MonthReportItem> monthReportItems = reportService.createMonthlyReports(from, to, 2);
        assertThat(monthReportItems).hasSize(1);
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(monthReportItems.get(0).getProjectReportItems())).isEqualTo(90);
        assertThat(monthReportItems.get(0).getProjectReportItems().size()).isEqualTo(1);
    }

    @Test
    public void testYearlyReports() {
        List<YearReportItem> yearReportItems = reportService.createYearlyReports(from, to);
        assertThat(yearReportItems).hasSize(2);
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(yearReportItems.get(0).getProjectReportItems())).isEqualTo(90);
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(yearReportItems.get(1).getProjectReportItems())).isEqualTo(360);
    }

    @Test
    public void testProjectsReports() {
        List<ProjectReportItem> projectReportItems = reportService.createProjectsReports(from, to);
        assertThat(projectReportItems).hasSize(2);
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(projectReportItems)).isEqualTo(450);
        assertThat(ReportServiceTestsHelper.countTotalSpentTimeWorkItems(projectReportItems.get(0).getWorkReportItems())).isEqualTo(270);
        assertThat(ReportServiceTestsHelper.countTotalSpentTimeWorkItems(projectReportItems.get(1).getWorkReportItems())).isEqualTo(180);
    }

    @Test
    public void testUsersReports() {
        List<UserReportItem> userReportItems = reportService.createUsersReports(from, to);
        assertThat(userReportItems).hasSize(2);
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(userReportItems.get(0).getProjectReportItems())).isEqualTo(90);
        assertThat(userReportItems.get(0).getUser().getName()).isEqualTo("druhy");
        assertThat(userReportItems.get(1).getUser().getName()).isEqualTo("test");
        assertThat(ReportServiceTestsHelper.countTotalSpentTime(userReportItems.get(1).getProjectReportItems())).isEqualTo(360);
    }

}
