package cz.cvut.fit.timetracking.user.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Integer id) {
        this("User with id = " + id + " not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
