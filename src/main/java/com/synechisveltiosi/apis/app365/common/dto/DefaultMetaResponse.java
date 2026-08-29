package com.synechisveltiosi.apis.app365.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.users.dto.UserActionMetaResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultMetaResponse {

    @JsonProperty("lastComment")
    private CommentResponse lastComment;

    @JsonProperty("user")
    private UserActionMetaResponse user;

    public CommentResponse getLastComment() {
        return lastComment;
    }

    public void setLastComment(CommentResponse lastComment) {
        this.lastComment = lastComment;
    }

    public DefaultMetaResponse withLastComment(CommentResponse lastComment) {
        this.lastComment = lastComment;
        return this;
    }

    public UserActionMetaResponse getUser() {
        return user;
    }

    public void setUser(UserActionMetaResponse user) {
        this.user = user;
    }

    public DefaultMetaResponse withUser(UserActionMetaResponse user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("lastComment", lastComment)
                .append("user", user)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(user)
                .append(lastComment)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DefaultMetaResponse)) {
            return false;
        }

        DefaultMetaResponse rhs = ((DefaultMetaResponse) other);
        return new EqualsBuilder()
                .append(user, rhs.user)
                .append(lastComment, rhs.lastComment)
                .isEquals();
    }
}
