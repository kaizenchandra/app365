package com.synechisveltiosi.apis.app365.common.service.social.Google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.synechisveltiosi.apis.app365.common.service.social.SocialNetworkService;
import com.synechisveltiosi.apis.app365.common.util.RegistrationChannel;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStreamReader;

@Service
public class GoogleService implements SocialNetworkService {

    private final String oauth2TokenEndpoint;
    private final String oauth2ConfigFilePath;

    public GoogleService(@Value("${app365.google.oauth2.token-endpoint}") String oauth2TokenEndpoint,
                         @Value("${app365.google.oauth2.config-file-path}") String oauth2ConfigFilePath) {

        this.oauth2TokenEndpoint = oauth2TokenEndpoint;
        this.oauth2ConfigFilePath = oauth2ConfigFilePath;
    }

    @Override
    public User loadWithToken(String token) {
        try {
            // Exchange auth code for access token
            Resource resource = new ClassPathResource(oauth2ConfigFilePath);

            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(),
                            new InputStreamReader(resource.getInputStream()));

            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), JacksonFactory.getDefaultInstance(), oauth2TokenEndpoint,
                    clientSecrets.getDetails().getClientId(), clientSecrets.getDetails().getClientSecret(),
                    token, "")
                    .execute();

            // Get profile info from ID token
            GoogleIdToken idToken = tokenResponse.parseIdToken();
            GoogleIdToken.Payload payload = idToken.getPayload();

            return deserializeUser(payload);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private User deserializeUser(GoogleIdToken.Payload payload) {
        User user = new User();
        user.setFirstName((String) payload.get("given_name"));
        user.setLastName((String) payload.get("family_name"));
        user.setEmail(payload.getEmail());
        user.setChannel(RegistrationChannel.GOOGLE);
        user.setSocialIdentifier(payload.getSubject());

        // Retrieve user photo
        String picture = (String) payload.get("picture");
        if (!StringUtils.isEmpty(picture)) {
            user.setProfilePicture(picture);
        }

        return user;
    }
}
