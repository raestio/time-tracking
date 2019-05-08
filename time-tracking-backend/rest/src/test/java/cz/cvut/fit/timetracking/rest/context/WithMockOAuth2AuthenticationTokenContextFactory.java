package cz.cvut.fit.timetracking.rest.context;

import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WithMockOAuth2AuthenticationTokenContextFactory implements WithSecurityContextFactory<WithMockOAuth2AuthenticationToken> {

    @Override
    public SecurityContext createSecurityContext(WithMockOAuth2AuthenticationToken annotation) {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(annotation.userId());
        userPrincipal.setEmail(annotation.email());
        userPrincipal.setAuthorities(Stream.of(annotation.authorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userPrincipal, userPrincipal.getAuthorities(), annotation.registrationId());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
