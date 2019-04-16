package cz.cvut.fit.timetracking.security.oauth2.exception;

public class OAuth2BadRequestException extends RuntimeException {

    public OAuth2BadRequestException(String message) {
        super(message);
    }
}
