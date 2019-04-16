package cz.cvut.fit.timetracking.security.oauth2.user;

import cz.cvut.fit.timetracking.security.oauth2.Provider;
import cz.cvut.fit.timetracking.security.oauth2.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (Provider.GOOGLE.toString().equalsIgnoreCase(registrationId)) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Login with " + registrationId + " is not supported yet");
        }
    }
}
