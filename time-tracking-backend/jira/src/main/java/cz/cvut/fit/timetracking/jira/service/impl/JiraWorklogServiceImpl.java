package cz.cvut.fit.timetracking.jira.service.impl;

import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;
import com.atlassian.jira.rest.client.api.domain.Worklog;
import cz.cvut.fit.timetracking.jira.exception.JiraUserException;
import cz.cvut.fit.timetracking.jira.service.JiraIssueService;
import cz.cvut.fit.timetracking.jira.service.JiraUserService;
import cz.cvut.fit.timetracking.jira.service.JiraWorklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JiraWorklogServiceImpl implements JiraWorklogService {
    private static final String JQL_WORKLOG_AUTHOR_FORMAT = "worklogAuthor = %s";
    private static final String JQL_WORKLOG_AUTHOR_DATE_FROM_FORMAT = "worklogAuthor = %s AND worklogDate >= %s";
    private static final String JQL_WORKLOG_AUTHOR_DATE_TO_FORMAT = "worklogAuthor = %s AND worklogDate < %s";
    private static final String JQL_WORKLOG_AUTHOR_DATE_FROM_TO_FORMAT = "worklogAuthor = %s AND worklogDate >= %s AND worklogDate < %s";

    @Autowired
    private JiraUserService jiraUserService;

    @Autowired
    private JiraIssueService jiraIssueService;

    @Override
    public List<Worklog> findWorklogsByUserEmail(String email) {
        return findWorklogsByUserEmail(email, null);
    }

    @Override
    public List<Worklog> findWorklogsByUserEmail(String email, LocalDate fromInclusive) {
        return findWorklogsByUserEmail(email, fromInclusive, null);
    }

    @Override
    public List<Worklog> findWorklogsByUserEmail(String email, LocalDate fromInclusive, LocalDate toExclusive) {
        return findWorklogsByUserEmailInternal(email, fromInclusive, toExclusive);
    }

    private List<Worklog> findWorklogsByUserEmailInternal(String email, LocalDate fromInclusive, LocalDate toExclusive) {
        User user = jiraUserService.findUserByEmail(email).orElseThrow(() -> new JiraUserException("JIRA user with email " + email + " not found."));
        List<Issue> issues = jiraIssueService.findAllIssuesWithJQLQuery(buildJqlQuery(user, fromInclusive, toExclusive));
        return fetchIssuesWorklogs(issues);
    }

    private List<Worklog> fetchIssuesWorklogs(List<Issue> issues) {
        List<String> issuesKeys = issues.stream().map(BasicIssue::getKey).collect(Collectors.toList());
        return fetchIssuesWorklogsByIssueKeys(issuesKeys);
    }

    private List<Worklog> fetchIssuesWorklogsByIssueKeys(List<String> issuesKeys) {
        List<Worklog> worklogs = issuesKeys.stream().map(this::fetchWorklogsForIssue).flatMap(List::stream).collect(Collectors.toList());
        return worklogs;
    }

    private List<Worklog> fetchWorklogsForIssue(String issueKey) {
        Issue issue = jiraIssueService.findIssueByKey(issueKey);
        List<Worklog> worklogs = StreamSupport.stream(issue.getWorklogs().spliterator(), false).collect(Collectors.toList());
        return worklogs;
    }

    private String buildJqlQuery(User user, LocalDate fromInclusive, LocalDate toExclusive) {
        Assert.notNull(user, "User cannot be null in JQL query");
        String jqlQuery;
        if (fromInclusive == null && toExclusive == null) {
            jqlQuery = String.format(JQL_WORKLOG_AUTHOR_FORMAT, user.getName());
        } else if (fromInclusive != null && toExclusive == null) {
            jqlQuery = String.format(JQL_WORKLOG_AUTHOR_DATE_FROM_FORMAT, user.getName(), fromInclusive.toString());
        } else if (fromInclusive == null) {
            jqlQuery = String.format(JQL_WORKLOG_AUTHOR_DATE_TO_FORMAT, user.getName(), toExclusive.toString());
        } else {
            jqlQuery = String.format(JQL_WORKLOG_AUTHOR_DATE_FROM_TO_FORMAT, user.getName(), fromInclusive.toString(), toExclusive.toString());
        }
        return jqlQuery;
    }
}
