package cz.cvut.fit.timetracking.workrecord.exception;

public class WorkRecordNotFoundException extends RuntimeException {

    public WorkRecordNotFoundException(Integer id) {
        this("Work record with id " + id + " not found.");
    }

    public WorkRecordNotFoundException(String message) {
        super(message);
    }
}
