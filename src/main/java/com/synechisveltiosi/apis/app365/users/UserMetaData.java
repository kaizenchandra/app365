package com.synechisveltiosi.apis.app365.users;

import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.synechisveltiosi.apis.app365.common.util.UserSocialToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserMetaData implements Serializable {

    private static final long serialVersionUID = 0L;

    private List<UserSocialToken> socialTokens;

    public List<UserSocialToken> getSocialTokens() {
        return socialTokens;
    }

    public void setSocialTokens(List<UserSocialToken> socialTokens) {
        this.socialTokens = socialTokens;
    }

    public void addSocialToken(UserSocialToken socialToken) {
        if (socialToken == null) throw new IllegalArgumentException("Social token cannot be null");

        // Initialize social tokens list if it is null
        if (socialTokens == null) socialTokens = new ArrayList<>();

        // Add the token
        if (!socialTokens.contains(socialToken))
            socialTokens.add(socialToken);
    }

    public void removeSocialToken(SocialNetworkProvider provider) {
        if (provider == null) throw new IllegalArgumentException("Social network provider cannot be null");

        // Create the token
        UserSocialToken token = new UserSocialToken();
        token.setProvider(provider);

        // Remove token
        socialTokens.remove(token);
    }
}
