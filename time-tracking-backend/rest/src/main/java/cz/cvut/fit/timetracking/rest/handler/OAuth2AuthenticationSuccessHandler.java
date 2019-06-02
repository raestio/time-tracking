package cz.cvut.fit.timetracking.rest.handler;

import cz.cvut.fit.timetracking.rest.component.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import cz.cvut.fit.timetracking.rest.utils.CookieUtils;
import cz.cvut.fit.timetracking.security.oauth2.exception.OAuth2BadRequestException;
import cz.cvut.fit.timetracking.security.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${time-tracking.security.oauth2.authorizedRedirectUris}")
    private List<String> authorizedRedirectUris = new ArrayList<>();

    @Autowired
    private TokenService tokenService;

    @Autowired
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);
        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new OAuth2BadRequestException("Can't proceed with the authentication - Unauthorized Redirect URI");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        String token = tokenService.createToken(authentication);
        String finalTargetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString();
        return finalTargetUrl;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        boolean isAuthorized = authorizedRedirectUris.stream().anyMatch(authorizedRedirectUri -> matchesClientRedirectUri(clientRedirectUri, authorizedRedirectUri));
        return isAuthorized;
    }

    private boolean matchesClientRedirectUri(URI clientRedirectUri, String authorizedRedirectUri) {
        URI authorizedURI = URI.create(authorizedRedirectUri);
        var matches = authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) && authorizedURI.getPort() == clientRedirectUri.getPort();
        return matches;
    }
}
