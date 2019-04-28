package cz.cvut.fit.timetracking.configuration;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import cz.cvut.fit.timetracking.jira.constants.PackageNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@ComponentScan(value = PackageNames.JIRA_CORE)
public class JiraConfiguration {

    @Value("${time-tracking.jira.restClient.authentication.username}")
    private String username;

    @Value("${time-tracking.jira.restClient.authentication.password}")
    private String password;

    @Value("${time-tracking.jira.restClient.serverUri}")
    private String serverUri;

    @Bean
    public JiraRestClient jiraRestClient() {
        JiraRestClientFactory jiraRestClientFactory = new AsynchronousJiraRestClientFactory();
        JiraRestClient jiraRestClient = jiraRestClientFactory.createWithBasicHttpAuthentication(URI.create(serverUri), username, password);
        return jiraRestClient;
    }

    @Bean
    public UserRestClient userRestClient() {
        return jiraRestClient().getUserClient();
    }

    @Bean
    public SearchRestClient searchRestClient() {
        return jiraRestClient().getSearchClient();
    }

    @Bean
    public IssueRestClient issueRestClient() {
        return jiraRestClient().getIssueClient();
    }
}
