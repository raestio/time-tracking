package cz.cvut.fit.timetracking.jira.service;

import com.atlassian.jira.rest.client.api.domain.User;

import java.util.Optional;

public interface JiraUserService {
    Optional<User> findUserByEmail(String email);
}
