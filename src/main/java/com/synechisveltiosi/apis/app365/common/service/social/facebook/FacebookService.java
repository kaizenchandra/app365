package com.synechisveltiosi.apis.app365.common.service.social.facebook;

import com.synechisveltiosi.apis.app365.common.service.social.SocialNetworkService;
import com.synechisveltiosi.apis.app365.common.util.RegistrationChannel;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FacebookService implements SocialNetworkService {

    private static final String FACEBOOK_USER_PROFILE_URL_TEMPLATE = "https://graph.facebook.com/%s/picture?type=large";

    public static final String[] fields = new String[]{"id", "first_name", "last_name", "email", "gender", "birthday"};

    private final FacebookConnectionFactory factory;

    public FacebookService(FacebookConnectionFactory factory) {
        this.factory = factory;
    }

    @Override
    public User loadWithToken(String token) {
        FacebookUser facebookUser;
        AccessGrant accessGrant = new AccessGrant(token);
        Connection<Facebook> connection = factory.createConnection(accessGrant);

        try {
            Facebook api = connection.getApi();
            facebookUser = api.fetchObject("/me", FacebookUser.class, fields);

            // Make user is not null
            if (facebookUser == null || StringUtils.isEmpty(facebookUser.getId())) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }

        // Deserialize facebook user
        User user = deserializeUser(facebookUser);
        user.setProfilePicture(String.format(FACEBOOK_USER_PROFILE_URL_TEMPLATE, facebookUser.getId()));

        return user;
    }

    private User deserializeUser(FacebookUser facebookUser) {

        User user = new User();
        user.setFirstName(facebookUser.getFirstName());
        user.setLastName(facebookUser.getLastName());
        user.setEmail(facebookUser.getEmail());
        user.setChannel(RegistrationChannel.FACEBOOK);
        user.setSocialIdentifier(facebookUser.getId());

        // Retrieve gender
        if (!StringUtils.isEmpty(facebookUser.getGender())) {
            String g = facebookUser.getGender();
//            user.setGender("male".equalsIgnoreCase(g) || "m".equalsIgnoreCase(g) ? "M" : "F");
        }

        // Retrieve birth date
        if (!StringUtils.isEmpty(facebookUser.getBirthday())) {
            try {
//                user.setBirthDate(DateUtil.format(facebookUser.getBirthday()));
            } catch (Exception ex) {
//                user.setBirthDate(null);
            }
        }

        return user;
    }
}
