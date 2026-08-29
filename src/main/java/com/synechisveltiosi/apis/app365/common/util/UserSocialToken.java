package com.synechisveltiosi.apis.app365.common.util;

import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class UserSocialToken implements Serializable {

    private static final long serialVersionUID = 0L;

    @JsonProperty("channel")
    private SocialNetworkProvider provider;
    
    private String token;

    public SocialNetworkProvider getProvider() {
        return provider;
    }

    public void setProvider(SocialNetworkProvider provider) {
        this.provider = provider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonIgnore
    public boolean isValid() {
        if (provider != null && !StringUtils.isBlank(token)) return true;

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserSocialToken that = (UserSocialToken) o;

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
                .append("token", token)
                .toString();
    }
}
