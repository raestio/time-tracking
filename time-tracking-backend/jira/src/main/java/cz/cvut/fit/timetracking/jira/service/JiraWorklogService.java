package cz.cvut.fit.timetracking.jira.service;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Worklog;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface JiraWorklogService {
    Map<Issue, List<Worklog>> findWorklogsByUserEmail(String email);
    Map<Issue, List<Worklog>> findWorklogsByUserEmail(String email, LocalDate fromInclusive);
    Map<Issue, List<Worklog>> findWorklogsByUserEmail(String email, LocalDate fromInclusive, LocalDate toExclusive);
}
