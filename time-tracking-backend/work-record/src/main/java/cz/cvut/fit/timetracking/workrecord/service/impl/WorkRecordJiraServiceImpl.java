package cz.cvut.fit.timetracking.workrecord.service.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.api.domain.Worklog;
import cz.cvut.fit.timetracking.jira.service.JiraUserService;
import cz.cvut.fit.timetracking.jira.service.JiraWorklogService;
import cz.cvut.fit.timetracking.user.service.UserService;
import cz.cvut.fit.timetracking.workrecord.dto.Project;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecordConflictInfo;
import cz.cvut.fit.timetracking.workrecord.exception.JiraUserNotFoundException;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordJiraService;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class WorkRecordJiraServiceImpl implements WorkRecordJiraService {

    @Autowired
    private JiraWorklogService jiraWorklogService;

    @Autowired
    private JiraUserService jiraUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkRecordService workRecordService;

    @Override
    public boolean isUserAvailableInJira(Integer userId) {
        boolean isUserAvailable = findJiraUser(userId).isPresent();
        return isUserAvailable;
    }

    @Override
    public List<WorkRecordConflictInfo> findWorkRecordsToImport(LocalDate fromInclusive, LocalDate toExclusive, Integer userId) {
        User user = findJiraUser(userId).orElseThrow(() -> new JiraUserNotFoundException(userId));
        Map<Issue, List<Worklog>> worklogsGroupedByIssue = jiraWorklogService.findWorklogsByUserEmail(user.getEmailAddress(), fromInclusive, toExclusive);
        List<WorkRecord> workRecords = createIncompleteWorkRecords(worklogsGroupedByIssue);
        List<WorkRecordConflictInfo> workRecordConflictInfos = workRecords.stream()
                .map(w -> createWorkRecordConflictInfo(w, userId))
                .sorted(Comparator.comparing(workRecordConflictInfo -> workRecordConflictInfo.getWorkRecord().getDateFrom()))
                .collect(Collectors.toList());
        return workRecordConflictInfos;
    }

    private WorkRecordConflictInfo createWorkRecordConflictInfo(WorkRecord workRecord, Integer userId) {
        WorkRecordConflictInfo workRecordConflictInfo = new WorkRecordConflictInfo();
        workRecordConflictInfo.setWorkRecord(workRecord);
        boolean overlap = workRecordService.workRecordTimesOverlapWithOtherUserRecords(workRecord.getDateFrom(), workRecord.getDateTo(), userId);
        workRecordConflictInfo.setInConflict(overlap);
        return workRecordConflictInfo;
    }

    private List<WorkRecord> createIncompleteWorkRecords(Map<Issue, List<Worklog>> worklogsGroupedByIssue) {
        List<WorkRecord> workRecords = new ArrayList<>();
        for (Map.Entry<Issue, List<Worklog>> worklogsEntry : worklogsGroupedByIssue.entrySet()) {
            List<Worklog> worklogs = worklogsEntry.getValue();
            for (Worklog worklog : worklogs) {
                workRecords.add(createIncompleteWorkRecord(worklogsEntry.getKey(), worklog));
            }
        }
        return workRecords;
    }

    private WorkRecord createIncompleteWorkRecord(Issue issue, Worklog worklog) {
        WorkRecord workRecord = new WorkRecord();
        workRecord.setDateFrom(toLocalDateTime(worklog.getStartDate()));
        workRecord.setDateTo(toLocalDateTime(worklog.getStartDate()).plusMinutes(worklog.getMinutesSpent()));
        if (StringUtils.isNotBlank(worklog.getComment())) {
            workRecord.setDescription(issue.getKey() + " " + worklog.getComment());
        } else {
            workRecord.setDescription(issue.getKey());
        }

        Project project = new Project();
        project.setId(issue.getProject().getId().intValue());
        project.setName(issue.getProject().getName());
        workRecord.setProject(project);
        return workRecord;
    }

    private LocalDateTime toLocalDateTime(DateTime dateTime) {
        dateTime = dateTime.withZone(DateTimeZone.forTimeZone(TimeZone.getDefault()));
        LocalDateTime localDateTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), dateTime.getHourOfDay(), dateTime.getMinuteOfHour());
        return localDateTime;
    }

    private Optional<User> findJiraUser(Integer userId) {
        Optional<User> user = userService.findById(userId).map(u -> {
            String email = u.getEmail();
            return jiraUserService.findUserByEmail(email);
        }).orElse(Optional.empty());
        return user;
    }
}
