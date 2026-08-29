package com.synechisveltiosi.apis.app365.users.dto;

import com.synechisveltiosi.apis.app365.common.util.mapper.UserSocialTokenResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMetaDataResponse {

    @JsonProperty("socialTokens")
    private List<UserSocialTokenResponse> socialTokens;

    public List<UserSocialTokenResponse> getSocialTokens() {
        return socialTokens;
    }

    public void setSocialTokens(List<UserSocialTokenResponse> socialTokens) {
        this.socialTokens = socialTokens;
    }
}
