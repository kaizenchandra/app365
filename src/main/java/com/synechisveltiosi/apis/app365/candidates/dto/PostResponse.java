
package com.synechisveltiosi.apis.app365.candidates.dto;

import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
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
public class PostResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("channel")
    private SocialNetworkProvider channel;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    @JsonProperty("reportedAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date reportedAt;

    @JsonProperty("owner")
    private UserOwnerResponse owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PostResponse withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostResponse withContent(String content) {
        this.content = content;
        return this;
    }

    public SocialNetworkProvider getChannel() {
        return channel;
    }

    public void setChannel(SocialNetworkProvider channel) {
        this.channel = channel;
    }

    public PostResponse withChannel(SocialNetworkProvider channel) {
        this.channel = channel;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PostResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(Date reportedAt) {
        this.reportedAt = reportedAt;
    }

    public PostResponse withReportedAt(Date reportedAt) {
        this.reportedAt = reportedAt;
        return this;
    }

    public UserOwnerResponse getOwner() {
        return owner;
    }

    public void setOwner(UserOwnerResponse owner) {
        this.owner = owner;
    }

    public PostResponse withOwner(UserOwnerResponse owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .append("content", content)
                .append("createdAt", createdAt)
                .append("owner", owner)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(createdAt)
                .append(id)
                .append(title)
                .append(content)
                .append(owner)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PostResponse)) {
            return false;
        }

        PostResponse rhs = ((PostResponse) other);
        return new EqualsBuilder()
                .append(createdAt, rhs.createdAt)
                .append(id, rhs.id)
                .append(title, rhs.title)
                .append(content, rhs.content)
                .append(owner, rhs.owner)
                .isEquals();
    }
}
