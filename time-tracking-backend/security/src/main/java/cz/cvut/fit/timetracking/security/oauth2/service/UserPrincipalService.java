package cz.cvut.fit.timetracking.security.oauth2.service;

import cz.cvut.fit.timetracking.user.dto.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public interface UserPrincipalService {
    OAuth2User create(User user, Map<String, Object> attributes);

    OAuth2User create(User user);
}
