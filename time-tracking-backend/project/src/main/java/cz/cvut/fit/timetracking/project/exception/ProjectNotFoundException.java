package cz.cvut.fit.timetracking.project.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Integer id) {
        this("Project with id = " + id + " not found");
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }
}
