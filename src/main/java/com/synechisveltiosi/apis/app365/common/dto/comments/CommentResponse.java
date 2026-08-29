
package com.synechisveltiosi.apis.app365.common.dto.comments;

import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import com.synechisveltiosi.apis.app365.users.dto.UserOwnerResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    @JsonProperty("owner")
    private UserOwnerResponse owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CommentResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentResponse withContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public CommentResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public UserOwnerResponse getOwner() {
        return owner;
    }

    public void setOwner(UserOwnerResponse owner) {
        this.owner = owner;
    }

    public CommentResponse withOwner(UserOwnerResponse owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("content", content)
                .append("createdAt", createdAt)
                .append("owner", owner)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(owner)
                .append(createdAt)
                .append(id)
                .append(content)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CommentResponse)) {
            return false;
        }

        CommentResponse rhs = ((CommentResponse) other);
        return new EqualsBuilder()
                .append(owner, rhs.owner)
                .append(createdAt, rhs.createdAt)
                .append(id, rhs.id)
                .append(content, rhs.content)
                .isEquals();
    }
}
