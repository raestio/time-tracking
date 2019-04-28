package cz.cvut.fit.timetracking.jira.service.impl;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import cz.cvut.fit.timetracking.jira.service.JiraIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JiraIssueServiceImpl implements JiraIssueService {
    private static final int SEARCH_ISSUES_MAX_RESULTS = 500;

    @Autowired
    private IssueRestClient issueRestClient;

    @Autowired
    private SearchRestClient searchRestClient;

    @Override
    public List<Issue> findAllIssuesWithJQLQuery(String jqlQuery) {
        List<Issue> issues = new ArrayList<>();
        boolean allIssuesAreFetched = false;
        int startAt = 0;
        while (!allIssuesAreFetched) {
            SearchResult searchResult = searchRestClient.searchJql(jqlQuery, SEARCH_ISSUES_MAX_RESULTS, startAt, null).claim();
            List<Issue> issuesResult = StreamSupport.stream(searchResult.getIssues().spliterator(), false).collect(Collectors.toList());
            issues.addAll(issuesResult);
            allIssuesAreFetched = issuesResult.size() < SEARCH_ISSUES_MAX_RESULTS;
            startAt += SEARCH_ISSUES_MAX_RESULTS;
        }
        return issues;
    }

    @Override
    public Issue findIssueByKey(String issueKey) {
        Issue issue = issueRestClient.getIssue(issueKey).claim();
        return issue;
    }

}
