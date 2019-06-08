package cz.cvut.fit.timetracking.rest.controller;

import cz.cvut.fit.timetracking.report.dto.DayReportItem;
import cz.cvut.fit.timetracking.report.dto.MonthReportItem;
import cz.cvut.fit.timetracking.report.dto.ProjectReportItem;
import cz.cvut.fit.timetracking.report.dto.UserReportItem;
import cz.cvut.fit.timetracking.report.dto.YearReportItem;
import cz.cvut.fit.timetracking.report.service.ReportService;
import cz.cvut.fit.timetracking.rest.dto.report.DayReportItemDTO;
import cz.cvut.fit.timetracking.rest.dto.report.MonthReportItemDTO;
import cz.cvut.fit.timetracking.rest.dto.report.ProjectReportItemDTO;
import cz.cvut.fit.timetracking.rest.dto.report.UserReportItemDTO;
import cz.cvut.fit.timetracking.rest.dto.report.YearReportItemDTO;
import cz.cvut.fit.timetracking.rest.dto.report.response.DailyReportsResponse;
import cz.cvut.fit.timetracking.rest.dto.report.response.MonthlyReportsResponse;
import cz.cvut.fit.timetracking.rest.dto.report.response.ProjectsReportsResponse;
import cz.cvut.fit.timetracking.rest.dto.report.response.UsersReportsResponse;
import cz.cvut.fit.timetracking.rest.dto.report.response.YearlyReportsResponse;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import cz.cvut.fit.timetracking.rest.utils.TimeUtils;
import cz.cvut.fit.timetracking.security.CurrentUser;
import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reports")
public class ReportController {
    public static final int DAILY_REPORTS_DEFAULT_DAYS = 30;
    public static final int MONTHLY_REPORTS_DEFAULT_MONTHS = 12;
    public static final int YEARLY_REPORTS_DEFAULT_YEARS = 5;

    @Autowired
    private ReportService reportService;

    @Autowired
    private RestModelMapper restModelMapper;

    @GetMapping("/monthly")
    @PreAuthorize("hasAuthority('ADMIN') or @securityAccessServiceImpl.itIsMe(#userId) or @securityAccessServiceImpl.hasProjectRole(#projectId, 'PROJECT_MANAGER')")
    public ResponseEntity<MonthlyReportsResponse> getMonthlyReports(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromInclusive,
                                                                    @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toExclusive,
                                                                    @RequestParam(value = "userId", required = false) Integer userId,
                                                                    @RequestParam(value = "projectId", required = false) Integer projectId) {

        MonthlyReportsResponse monthlyReportsResponse = createMonthlyReports(fromInclusive, toExclusive, userId, projectId);
        return ResponseEntity.ok(monthlyReportsResponse);
    }

    @GetMapping("/daily")
    @PreAuthorize("hasAuthority('ADMIN') or @securityAccessServiceImpl.itIsMe(#userId) or @securityAccessServiceImpl.hasProjectRole(#projectId, 'PROJECT_MANAGER')")
    public ResponseEntity<DailyReportsResponse> getDailyReports(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromInclusive,
                                                                @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toExclusive,
                                                                @RequestParam(value = "userId", required = false) Integer userId,
                                                                @RequestParam(value = "projectId", required = false) Integer projectId) {
        DailyReportsResponse dailyReportsResponse = createDailyReports(fromInclusive, toExclusive, userId, projectId);
        return ResponseEntity.ok(dailyReportsResponse);
    }

    @GetMapping("/yearly")
    @PreAuthorize("hasAuthority('ADMIN') or @securityAccessServiceImpl.itIsMe(#userId) or @securityAccessServiceImpl.hasProjectRole(#projectId, 'PROJECT_MANAGER')")
    public ResponseEntity<YearlyReportsResponse> getYearlyReports(@RequestParam(value = "from", required = false) @Positive Integer fromInclusive,
                                                                  @RequestParam(value = "to", required = false) @Positive Integer toExclusive,
                                                                  @RequestParam(value = "userId", required = false) Integer userId,
                                                                  @RequestParam(value = "projectId", required = false) Integer projectId) {
        YearlyReportsResponse yearlyReportsResponse = createYearlyReports(fromInclusive, toExclusive, userId, projectId);
        return ResponseEntity.ok(yearlyReportsResponse);
    }

    @GetMapping("/projects")
    @PreAuthorize("hasAuthority('ADMIN') or @securityAccessServiceImpl.hasProjectRole(#projectId, 'PROJECT_MANAGER')")
    public ResponseEntity<ProjectsReportsResponse> getProjectsReports(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromInclusive,
                                                                      @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toExclusive,
                                                                      @RequestParam(value = "projectId", required = false) Integer projectId) {

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate from = TimeUtils.getFrom(fromInclusive, toExclusive, tomorrow.minusDays(DAILY_REPORTS_DEFAULT_DAYS), date -> date.minusDays(DAILY_REPORTS_DEFAULT_DAYS));
        LocalDate to = TimeUtils.getTo(toExclusive, tomorrow);
        List<ProjectReportItem> projectReportItems = reportService.createProjectsReports(from, to, projectId);
        ProjectsReportsResponse projectsReportsResponse = new ProjectsReportsResponse();
        projectsReportsResponse.setProjectReportItems(projectReportItems.stream().map(p -> restModelMapper.map(p, ProjectReportItemDTO.class)).collect(Collectors.toList()));
        return ResponseEntity.ok(projectsReportsResponse);
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsersReportsResponse> getUsersReports(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromInclusive,
                                                                @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toExclusive) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate from = TimeUtils.getFrom(fromInclusive, toExclusive, tomorrow.minusDays(DAILY_REPORTS_DEFAULT_DAYS), date -> date.minusDays(DAILY_REPORTS_DEFAULT_DAYS));
        LocalDate to = TimeUtils.getTo(toExclusive, tomorrow);
        List<UserReportItem> userReportItems = reportService.createUsersReports(from, to);
        UsersReportsResponse usersReportsResponse = new UsersReportsResponse();
        usersReportsResponse.setUserReportItems(userReportItems.stream().map(u -> restModelMapper.map(u, UserReportItemDTO.class)).collect(Collectors.toList()));
        return ResponseEntity.ok(usersReportsResponse);
    }

    @GetMapping("/monthly/me")
    public ResponseEntity<MonthlyReportsResponse> getMyMonthlyReports(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromInclusive,
                                                      @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toExclusive,
                                                      @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(createMonthlyReports(fromInclusive, toExclusive, userPrincipal.getId()));
    }

    @GetMapping("/daily/me")
    public ResponseEntity<DailyReportsResponse> getMyDailyReports(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromInclusive,
                                                    @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toExclusive,
                                                    @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(createDailyReports(fromInclusive, toExclusive, userPrincipal.getId()));
    }

    @GetMapping("/yearly/me")
    public ResponseEntity<YearlyReportsResponse> getMyYearlyReports(@RequestParam(value = "from", required = false) @Positive Integer fromInclusive,
                                                     @RequestParam(value = "to", required = false) @Positive Integer toExclusive,
                                                     @CurrentUser UserPrincipal userPrincipal) {
        return ResponseEntity.ok(createYearlyReports(fromInclusive, toExclusive, userPrincipal.getId()));
    }

    private MonthlyReportsResponse createMonthlyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId, Integer projectId) {
        LocalDate nextMonth = LocalDate.now().plusMonths(1);
        LocalDate from = TimeUtils.getFrom(fromInclusive, toExclusive, nextMonth.minusMonths(MONTHLY_REPORTS_DEFAULT_MONTHS), date -> date.minusMonths(MONTHLY_REPORTS_DEFAULT_MONTHS));
        LocalDate to = TimeUtils.getTo(toExclusive, nextMonth);
        List<MonthReportItem> monthReportItems = reportService.createMonthlyReports(from, to, userId, projectId);
        MonthlyReportsResponse monthlyReportsResponse = new MonthlyReportsResponse();
        monthlyReportsResponse.setMonthlyReportItems(monthReportItems.stream().map(r -> restModelMapper.map(r, MonthReportItemDTO.class)).collect(Collectors.toList()));
        return monthlyReportsResponse;
    }

    private MonthlyReportsResponse createMonthlyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId) {
        return createMonthlyReports(fromInclusive, toExclusive, userId, null);
    }

    private DailyReportsResponse createDailyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId) {
        return createDailyReports(fromInclusive, toExclusive, userId, null);
    }

    private DailyReportsResponse createDailyReports(LocalDate fromInclusive, LocalDate toExclusive, Integer userId, Integer projectId) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate from = TimeUtils.getFrom(fromInclusive, toExclusive, tomorrow.minusDays(DAILY_REPORTS_DEFAULT_DAYS), date -> date.minusDays(DAILY_REPORTS_DEFAULT_DAYS));
        LocalDate to = TimeUtils.getTo(toExclusive, tomorrow);
        List<DayReportItem> dayReportItems = reportService.createDailyReports(from, to, userId, projectId);
        DailyReportsResponse dailyReportsResponse = new DailyReportsResponse();
        dailyReportsResponse.setDailyReportItems(dayReportItems.stream().map(r -> restModelMapper.map(r, DayReportItemDTO.class)).collect(Collectors.toList()));
        return dailyReportsResponse;
    }

    private YearlyReportsResponse createYearlyReports(Integer fromInclusive, Integer toExclusive, Integer userId, Integer projectId) {
        LocalDate nextYear = LocalDate.now().plusYears(1);
        LocalDate from = fromInclusive == null ? null : LocalDate.ofYearDay(fromInclusive, 1);
        LocalDate to = toExclusive == null ? null : LocalDate.ofYearDay(toExclusive, 1);
        from = TimeUtils.getFrom(from, to, nextYear.minusDays(YEARLY_REPORTS_DEFAULT_YEARS), date -> date.minusYears(YEARLY_REPORTS_DEFAULT_YEARS));
        to = TimeUtils.getTo(to, nextYear);
        List<YearReportItem> yearReportItems = reportService.createYearlyReports(from, to, userId, projectId);
        YearlyReportsResponse yearlyReportsResponse = new YearlyReportsResponse();
        yearlyReportsResponse.setYearlyReportItems(yearReportItems.stream().map(r -> restModelMapper.map(r, YearReportItemDTO.class)).collect(Collectors.toList()));
        return yearlyReportsResponse;
    }

    private YearlyReportsResponse createYearlyReports(Integer fromInclusive, Integer toExclusive, Integer userId) {
        return createYearlyReports(fromInclusive, toExclusive, userId, null);
    }



}
