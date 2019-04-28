package cz.cvut.fit.timetracking.jira.service;

import com.atlassian.jira.rest.client.api.domain.Issue;

import java.util.List;

public interface JiraIssueService {
    List<Issue> findAllIssuesWithJQLQuery(String jqlQuery);

    Issue findIssueByKey(String issueKey);
}
