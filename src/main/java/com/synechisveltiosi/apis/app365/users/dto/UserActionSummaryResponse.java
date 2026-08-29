package com.synechisveltiosi.apis.app365.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserActionSummaryResponse {

    @JsonProperty("joinCount")
    private Integer joinCount;

    @JsonProperty("shareCount")
    private Integer shareCount;

    @JsonProperty("commentCount")
    private Integer commentCount;

    @JsonProperty("likeCount")
    private Integer likeCount;

    public Integer getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
    }

    public UserActionSummaryResponse withJoinCount(Integer joinCount) {
        this.joinCount = joinCount;
        return this;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public UserActionSummaryResponse withShareCount(Integer shareCount) {
        this.shareCount = shareCount;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public UserActionSummaryResponse withCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public UserActionSummaryResponse withLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("joinCount", joinCount)
                .append("shareCount", shareCount)
                .append("commentCount", commentCount)
                .append("likeCount", likeCount)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(shareCount)
                .append(likeCount)
                .append(joinCount)
                .append(commentCount)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserActionSummaryResponse)) {
            return false;
        }

        UserActionSummaryResponse rhs = ((UserActionSummaryResponse) other);
        return new EqualsBuilder()
                .append(shareCount, rhs.shareCount)
                .append(likeCount, rhs.likeCount)
                .append(joinCount, rhs.joinCount)
                .append(commentCount, rhs.commentCount)
                .isEquals();
    }
}
