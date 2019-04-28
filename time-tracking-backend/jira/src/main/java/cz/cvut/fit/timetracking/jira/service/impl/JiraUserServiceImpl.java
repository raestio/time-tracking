package cz.cvut.fit.timetracking.jira.service.impl;

import com.atlassian.jira.rest.client.api.UserRestClient;
import com.atlassian.jira.rest.client.api.domain.User;
import cz.cvut.fit.timetracking.jira.exception.JiraUserException;
import cz.cvut.fit.timetracking.jira.service.JiraUserService;
import io.atlassian.util.concurrent.Promise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class JiraUserServiceImpl implements JiraUserService {

    @Autowired
    private UserRestClient userRestClient;

    @Override
    public Optional<User> findUserByEmail(String email) {
        Promise<Iterable<User>> usersPromise = userRestClient.findUsers(email);
        List<User> users = StreamSupport.stream(usersPromise.claim().spliterator(), false).collect(Collectors.toList());
        if (users.size() > 1) {
            throw new JiraUserException("JIRA cannot have 2 or more registered users with the same email. Conflicting email: " + email);
        }
        User result = users.isEmpty() ? null : users.get(0);
        return Optional.ofNullable(result);
    }
}
