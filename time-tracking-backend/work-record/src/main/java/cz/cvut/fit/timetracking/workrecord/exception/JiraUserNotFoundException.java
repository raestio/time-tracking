package cz.cvut.fit.timetracking.workrecord.exception;

public class JiraUserNotFoundException extends RuntimeException {

    public JiraUserNotFoundException(Integer id) {
        super("User with id = " + id + " not found in JIRA");
    }
}
