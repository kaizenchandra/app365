package com.synechisveltiosi.apis.app365.common.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangePasswordRequest {

    @JsonProperty("oldPassword")
    private String oldPassword;

    @JsonProperty("newPassword")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public ChangePasswordRequest withOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ChangePasswordRequest withNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("oldPassword", oldPassword)
                .append("newPassword", newPassword)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(newPassword)
                .append(oldPassword)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ChangePasswordRequest)) {
            return false;
        }

        ChangePasswordRequest rhs = ((ChangePasswordRequest) other);
        return new EqualsBuilder()
                .append(newPassword, rhs.newPassword)
                .append(oldPassword, rhs.oldPassword)
                .isEquals();
    }
}
