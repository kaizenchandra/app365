
package com.synechisveltiosi.apis.app365.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocialNetworkTokenResponse {

    @JsonProperty("socialNetworkToken")
    private String socialNetworkToken;

    public String getSocialNetworkToken() {
        return socialNetworkToken;
    }

    public void setSocialNetworkToken(String socialNetworkToken) {
        this.socialNetworkToken = socialNetworkToken;
    }

    public SocialNetworkTokenResponse withSocialNetworkToken(String socialNetworkToken) {
        this.socialNetworkToken = socialNetworkToken;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("socialNetworkToken", socialNetworkToken)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(socialNetworkToken)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SocialNetworkTokenResponse)) {
            return false;
        }

        SocialNetworkTokenResponse rhs = ((SocialNetworkTokenResponse) other);
        return new EqualsBuilder()
                .append(socialNetworkToken, rhs.socialNetworkToken)
                .isEquals();
    }
}
