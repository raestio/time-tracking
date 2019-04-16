package cz.cvut.fit.timetracking.security.service.impl;

import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import cz.cvut.fit.timetracking.security.service.UserPrincipalService;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.dto.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserPrincipalServiceImpl implements UserPrincipalService {

    @Override
    public OAuth2User create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(user.getId());
        userPrincipal.setEmail(user.getEmail());
        userPrincipal.setAttributes(attributes);
        userPrincipal.setAuthorities(createAuthorities(user.getUserRoles()));
        return userPrincipal;
    }

    private Collection<? extends GrantedAuthority> createAuthorities(List<UserRole> userRoles) {
        List<GrantedAuthority> grantedAuthorities = userRoles.stream().map(this::createSimpleGrantedAuthority).collect(Collectors.toList());
        return grantedAuthorities;
    }

    private SimpleGrantedAuthority createSimpleGrantedAuthority(UserRole userRole) {
        return new SimpleGrantedAuthority(userRole.getName().toString());
    }
}
