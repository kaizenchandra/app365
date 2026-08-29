package com.synechisveltiosi.apis.app365.accounts.config;

import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class SocialTokenConfig implements Serializable {

    private static final long serialVersionUID = 0L;

    private SocialNetworkProvider provider;
    private String appId;
    private String appSecret;
    private String accessToken;
    private String accessTokenSecret;
    private String userId;

    public SocialNetworkProvider getProvider() {
        return provider;
    }

    public void setProvider(SocialNetworkProvider provider) {
        this.provider = provider;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonIgnore
    public boolean isValid() {
        if (provider != null && !StringUtils.isBlank(accessToken)) return true;

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SocialTokenConfig that = (SocialTokenConfig) o;

        return new EqualsBuilder()
                .append(provider, that.provider)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(provider)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("provider", provider)
                .append("appId", appId)
                .append("appSecret", appSecret)
                .append("accessToken", accessToken)
                .append("accessTokenSecret", accessTokenSecret)
                .append("userId", userId)
                .toString();
    }
}
