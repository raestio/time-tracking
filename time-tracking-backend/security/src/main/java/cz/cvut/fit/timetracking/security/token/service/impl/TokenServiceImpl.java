package cz.cvut.fit.timetracking.security.token.service.impl;

import cz.cvut.fit.timetracking.security.token.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String createToken(Authentication authentication) {
        return null;
    }
}
