package cz.cvut.fit.timetracking.security.oauth2.user;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "given_name";
    public static final String KEY_SURNAME = "family_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PICTURE_URL = "picture";

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get(KEY_ID);
    }

    @Override
    public String getName() {
        return (String) attributes.get(KEY_NAME);
    }

    @Override
    public String getSurname() {
        return (String) attributes.get(KEY_SURNAME);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get(KEY_EMAIL);
    }

    @Override
    public String getPictureUrl() {
        return (String) attributes.get(KEY_PICTURE_URL);
    }
}
