package com.synechisveltiosi.apis.app365.candidates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateSummaryResponse {

    @JsonProperty("donationCount")
    private Integer donationCount;

    @JsonProperty("offerCount")
    private Integer offerCount;

    @JsonProperty("shareCount")
    private Integer shareCount;

    @JsonProperty("commentCount")
    private Integer commentCount;

    @JsonProperty("likeCount")
    private Integer likeCount;

    @JsonProperty("postCount")
    private Integer postCount;

    @JsonProperty("postCommentCount")
    private Integer postCommentCount;

    public Integer getDonationCount() {
        return donationCount;
    }

    public void setDonationCount(Integer donationCount) {
        this.donationCount = donationCount;
    }

    public CandidateSummaryResponse withDonationCount(Integer donationCount) {
        this.donationCount = donationCount;
        return this;
    }

    public Integer getOfferCount() {
        return offerCount;
    }

    public void setOfferCount(Integer offerCount) {
        this.offerCount = offerCount;
    }

    public CandidateSummaryResponse withOfferCount(Integer offerCount) {
        this.offerCount = offerCount;
        return this;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public CandidateSummaryResponse withShareCount(Integer shareCount) {
        this.shareCount = shareCount;
        return this;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public CandidateSummaryResponse withCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public CandidateSummaryResponse withLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
        return this;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public CandidateSummaryResponse withPostCount(Integer postCount) {
        this.postCount = postCount;
        return this;
    }

    public Integer getPostCommentCount() {
        return postCommentCount;
    }

    public void setPostCommentCount(Integer postCommentCount) {
        this.postCommentCount = postCommentCount;
    }

    public CandidateSummaryResponse withPostCommentCount(Integer postCommentCount) {
        this.postCommentCount = postCommentCount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CandidateSummaryResponse that = (CandidateSummaryResponse) o;

        return new EqualsBuilder()
                .append(donationCount, that.donationCount)
                .append(offerCount, that.offerCount)
                .append(shareCount, that.shareCount)
                .append(commentCount, that.commentCount)
                .append(likeCount, that.likeCount)
                .append(postCount, that.postCount)
                .append(postCommentCount, that.postCommentCount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(donationCount)
                .append(offerCount)
                .append(shareCount)
                .append(commentCount)
                .append(likeCount)
                .append(postCount)
                .append(postCommentCount)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("donationCount", donationCount)
                .append("offerCount", offerCount)
                .append("shareCount", shareCount)
                .append("commentCount", commentCount)
                .append("likeCount", likeCount)
                .append("postCount", postCount)
                .append("postCommentCount", postCommentCount)
                .toString();
    }
}
