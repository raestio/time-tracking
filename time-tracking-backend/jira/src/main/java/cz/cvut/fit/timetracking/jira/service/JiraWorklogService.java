package cz.cvut.fit.timetracking.jira.service;

import com.atlassian.jira.rest.client.api.domain.Worklog;

import java.time.LocalDate;
import java.util.List;

public interface JiraWorklogService {
    List<Worklog> findWorklogsByUserEmail(String email);

    List<Worklog> findWorklogsByUserEmail(String email, LocalDate fromInclusive);

    List<Worklog> findWorklogsByUserEmail(String email, LocalDate fromInclusive, LocalDate toInclusive);
}
