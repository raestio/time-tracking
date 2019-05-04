package cz.cvut.fit.timetracking.report.component;

import cz.cvut.fit.timetracking.report.dto.DayReportItem;
import cz.cvut.fit.timetracking.report.dto.MonthReportItem;
import cz.cvut.fit.timetracking.report.dto.ProjectReportItem;
import cz.cvut.fit.timetracking.report.dto.UserReportItem;
import cz.cvut.fit.timetracking.report.dto.WorkReportItem;
import cz.cvut.fit.timetracking.report.dto.YearReportItem;
import cz.cvut.fit.timetracking.workrecord.dto.Project;
import cz.cvut.fit.timetracking.workrecord.dto.User;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.dto.WorkType;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordGroupingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReportItemsHelper {

    @Autowired
    private WorkRecordGroupingService workRecordGroupingService;

    public DayReportItem createDayReportItem(LocalDate day, List<WorkRecord> workRecords) {
        workRecords.forEach(w -> Assert.isTrue(day.equals(w.getDateFrom().toLocalDate()), "days of work records must be same as the day parameter"));
        DayReportItem dayReportItem = new DayReportItem();
        dayReportItem.setDay(day);
        dayReportItem.setProjectReportItems(createProjectReportItems(workRecords));
        return dayReportItem;
    }

    public MonthReportItem createMonthReportItem(Month month, List<WorkRecord> workRecords) {
        workRecords.forEach(w -> Assert.isTrue(month.equals(w.getDateFrom().getMonth()), "months of work records must be same as the month parameter"));
        MonthReportItem monthReportItem = new MonthReportItem();
        monthReportItem.setMonth(month);
        monthReportItem.setProjectReportItems(createProjectReportItems(workRecords));
        return monthReportItem;
    }

    public YearReportItem createYearReportItem(Year year, List<WorkRecord> workRecords) {
        workRecords.forEach(w -> Assert.isTrue(year.equals(Year.of(w.getDateFrom().getYear())), "years of work records must be same as the year parameter"));
        YearReportItem yearReportItem = new YearReportItem();
        yearReportItem.setYear(year);
        yearReportItem.setProjectReportItems(createProjectReportItems(workRecords));
        return yearReportItem;
    }

    public ProjectReportItem createProjectReportItem(Project project, List<WorkRecord> workRecords) {
        workRecords.forEach(w -> Assert.isTrue(project.getId().equals(w.getProject().getId()), "projects of work records must be same as the project parameter"));
        ProjectReportItem projectWork = new ProjectReportItem();
        projectWork.setProject(mapToReportProject(project));
        List<WorkReportItem> works = createWorkReportItems(workRecords);
        projectWork.setWorkReportItems(works);
        return projectWork;
    }

    public WorkReportItem createWorkReportItem(WorkType workType, List<WorkRecord> workRecords) {
        workRecords.forEach(w -> Assert.isTrue(workType.getId().equals(w.getWorkType().getId()), "work types of work records must be same as the work type parameter"));
        WorkReportItem work = new WorkReportItem();
        work.setWorkType(mapToReportWorkType(workType));
        int minutesSpent = calculateMinutesSpent(workRecords);
        work.setMinutesSpent(minutesSpent);
        return work;
    }

    public UserReportItem createUserReportItem(User user, List<WorkRecord> workRecords) {
        workRecords.forEach(w -> Assert.isTrue(user.getId().equals(w.getUser().getId()), "users of work records must be same as the user parameter"));
        UserReportItem userReportItem = new UserReportItem();
        userReportItem.setUser(mapToReportUser(user));
        userReportItem.setProjectReportItems(createProjectReportItems(workRecords));
        return userReportItem;
    }

    public List<WorkReportItem> createWorkReportItems(List<WorkRecord> workRecords) {
        Map<WorkType, List<WorkRecord>> groupedWorkRecordsByWorkType = workRecordGroupingService.groupByWorkType(workRecords);
        return createWorkReportItems(groupedWorkRecordsByWorkType);
    }

    public List<ProjectReportItem> createProjectReportItems(List<WorkRecord> workRecords) {
        Map<Project, List<WorkRecord>> groupedWorkRecordsByProjects = workRecordGroupingService.groupByProjects(workRecords);
        return groupedWorkRecordsByProjects.entrySet().stream().map(this::createProjectReportItem).collect(Collectors.toList());
    }

    private ProjectReportItem createProjectReportItem(Map.Entry<Project, List<WorkRecord>> workRecordsGroupedByProject) {
        return createProjectReportItem(workRecordsGroupedByProject.getKey(), workRecordsGroupedByProject.getValue());
    }

    private List<WorkReportItem> createWorkReportItems(Map<WorkType, List<WorkRecord>> groupedWorkRecordsByWorkType) {
        return groupedWorkRecordsByWorkType.entrySet().stream().map(this::createWorkReportItem).collect(Collectors.toList());
    }

    private WorkReportItem createWorkReportItem(Map.Entry<WorkType, List<WorkRecord>> entry) {
        return createWorkReportItem(entry.getKey(), entry.getValue());
    }

    private int calculateMinutesSpent(List<WorkRecord> workRecords) {
        int minutesSpent = 0;
        for (WorkRecord workRecord : workRecords) {
            minutesSpent += ChronoUnit.MINUTES.between(workRecord.getDateFrom(), workRecord.getDateTo());
        }
        return minutesSpent;
    }

    private cz.cvut.fit.timetracking.report.dto.WorkType mapToReportWorkType(WorkType workType) {
        cz.cvut.fit.timetracking.report.dto.WorkType reportWorkType = new cz.cvut.fit.timetracking.report.dto.WorkType();
        reportWorkType.setId(workType.getId());
        reportWorkType.setName(workType.getName());
        return reportWorkType;
    }

    private cz.cvut.fit.timetracking.report.dto.Project mapToReportProject(Project project) {
        cz.cvut.fit.timetracking.report.dto.Project reportProject = new cz.cvut.fit.timetracking.report.dto.Project();
        reportProject.setId(project.getId());
        reportProject.setName(project.getName());
        return reportProject;
    }

    private cz.cvut.fit.timetracking.report.dto.User mapToReportUser(User user) {
        cz.cvut.fit.timetracking.report.dto.User reportUser = new cz.cvut.fit.timetracking.report.dto.User();
        reportUser.setId(user.getId());
        reportUser.setName(user.getName());
        reportUser.setSurname(user.getSurname());
        reportUser.setPictureUrl(user.getPictureUrl());
        return reportUser;
    }
}
