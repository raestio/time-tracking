package cz.cvut.fit.timetracking.security.oauth2.service;

import cz.cvut.fit.timetracking.security.oauth2.exception.OAuth2AuthenticationProcessingException;
import cz.cvut.fit.timetracking.security.oauth2.user.OAuth2UserInfo;
import cz.cvut.fit.timetracking.security.oauth2.user.OAuth2UserInfoFactory;
import cz.cvut.fit.timetracking.user.dto.AuthProvider;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserPrincipalService userPrincipalService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (StringUtils.isBlank(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider " + userRequest.getClientRegistration().getRegistrationId());
        }

        Optional<User> userOptional = userService.findByEmail(oAuth2UserInfo.getEmail());
        User user = userOptional.map(u -> updateExistingUser(u, oAuth2UserInfo, userRequest.getClientRegistration().getRegistrationId())).orElseGet(() -> registerNewUser(userRequest, oAuth2UserInfo));
        return userPrincipalService.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
        AuthProvider authProvider = AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        User user = userService.create(oAuth2UserInfo.getName(), oAuth2UserInfo.getSurname(), oAuth2UserInfo.getEmail(), authProvider, oAuth2UserInfo.getPictureUrl());
        return user;
    }

    private User updateExistingUser(User user, OAuth2UserInfo oAuth2UserInfo, String registrationId) {
        if (!user.getAuthProvider().toString().equalsIgnoreCase(registrationId)) {
            throw new OAuth2AuthenticationProcessingException("You are sign up with " + user.getAuthProvider() + " account. Please use your " + registrationId + " account to login.");
        }
        user.setName(oAuth2UserInfo.getName());
        user.setSurname(oAuth2UserInfo.getSurname());
        user.setPictureUrl(oAuth2UserInfo.getPictureUrl());
        User updatedUser = userService.createOrUpdate(user);
        return updatedUser;
    }
}
