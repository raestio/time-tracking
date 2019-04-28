package cz.cvut.fit.timetracking.jira;

import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.User;
import cz.cvut.fit.timetracking.configuration.JiraTestsConfiguration;
import io.atlassian.util.concurrent.Promise;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class JiraIntegrationTests extends JiraTestsConfiguration {

    @Autowired
    private UserRestClient userRestClient;

    @Autowired
    private SearchRestClient searchRestClient;

    @Test
    public void testFindUserByEmail() {
        Promise<Iterable<User>> usersPromise = userRestClient.findUsers("rastislav.zlacky@inventi.cz");
        Iterable<User> users = usersPromise.claim();
        assertThat(users).isNotEmpty();
    }

    @Test
    public void testFindIssuesByWorklogAuthor() {
        Promise<SearchResult> searchResultPromise = searchRestClient.searchJql("worklogAuthor = rastislav.zlacky", null, null, Set.of("summary", "issuetype", "created", "updated", "project", "status", "worklogs"));
        SearchResult searchResult = searchResultPromise.claim();
        assertThat(searchResult.getIssues()).isNotEmpty();
    }

}
