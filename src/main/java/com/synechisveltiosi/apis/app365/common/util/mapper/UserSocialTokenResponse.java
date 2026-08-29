package com.synechisveltiosi.apis.app365.common.util.mapper;

import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSocialTokenResponse {

    @JsonProperty("channel")
    private SocialNetworkProvider provider;

    public SocialNetworkProvider getProvider() {
        return provider;
    }

    public void setProvider(SocialNetworkProvider provider) {
        this.provider = provider;
    }
}
