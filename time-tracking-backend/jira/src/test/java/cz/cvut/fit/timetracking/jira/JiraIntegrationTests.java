package cz.cvut.fit.timetracking.jira;

import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.api.domain.User;
import cz.cvut.fit.timetracking.configuration.JiraTestsConfiguration;
import io.atlassian.util.concurrent.Promise;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JiraIntegrationTests extends JiraTestsConfiguration {

    @Autowired
    private UserRestClient userRestClient;

    @Test
    public void testFindUserByEmail() {
        Promise<Iterable<User>> usersPromise = userRestClient.findUsers("rastislav.zlacky@inventi.cz");
        Iterable<User> users = usersPromise.claim();
        assertThat(users).isNotEmpty();
    }

}
