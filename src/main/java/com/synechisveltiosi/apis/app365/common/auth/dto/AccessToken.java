
package com.synechisveltiosi.apis.app365.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public AccessToken withExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public AccessToken withTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public AccessToken withScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AccessToken withRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String prepareAccessToken() {
        return getTokenType() + " " + getAccessToken();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accessToken", accessToken)
                .append("expiresIn", expiresIn)
                .append("tokenType", tokenType)
                .append("scope", scope)
                .append("refreshToken", refreshToken)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(expiresIn)
                .append(accessToken)
                .append(tokenType)
                .append(scope)
                .append(refreshToken)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AccessToken)) {
            return false;
        }

        AccessToken rhs = ((AccessToken) other);
        return new EqualsBuilder()
                .append(expiresIn, rhs.expiresIn)
                .append(accessToken, rhs.accessToken)
                .append(tokenType, rhs.tokenType)
                .append(scope, rhs.scope)
                .append(refreshToken, rhs.refreshToken)
                .isEquals();
    }
}
