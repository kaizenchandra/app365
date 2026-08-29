
package com.synechisveltiosi.apis.app365.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FirebaseTokenResponse {

    @JsonProperty("firebaseToken")
    private String firebaseToken;

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public FirebaseTokenResponse withFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firebaseToken", firebaseToken)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(firebaseToken)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FirebaseTokenResponse)) {
            return false;
        }

        FirebaseTokenResponse rhs = ((FirebaseTokenResponse) other);
        return new EqualsBuilder()
                .append(firebaseToken, rhs.firebaseToken)
                .isEquals();
    }
}
