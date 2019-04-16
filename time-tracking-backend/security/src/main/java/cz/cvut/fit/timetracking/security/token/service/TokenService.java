package cz.cvut.fit.timetracking.security.token.service;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String createToken(Authentication authentication);
}
