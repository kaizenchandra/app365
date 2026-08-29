package com.synechisveltiosi.apis.app365.candidates.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_summary")
public class CandidateSummary extends BaseEntity {

    private static final long serialVersionUID = 0L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false, unique = true)
    private Candidate candidateId;

    @Column(name = "donation_count")
    private Long donationCount = 0L;

    @Column(name = "offer_count")
    private Long offerCount = 0L;

    @Column(name = "share_count")
    private Long shareCount = 0L;

    @Column(name = "comment_count")
    private Long commentCount = 0L;

    @Column(name = "like_count")
    private Long likeCount = 0L;

    @Column(name = "post_count")
    private Long postCount = 0L;

    @Column(name = "post_comment_count")
    private Long postCommentCount = 0L;

    @Column(name = "last_donation_at")
    private LocalDateTime lastDonationAt;

    @Column(name = "last_offer_at")
    private LocalDateTime lastOfferAt;

    @Column(name = "last_share_at")
    private LocalDateTime lastShareAt;

    @Column(name = "last_comment_at")
    private LocalDateTime lastCommentAt;

    @Column(name = "last_like_at")
    private LocalDateTime lastLikeAt;

    @Column(name = "last_post_at")
    private LocalDateTime lastPostAt;

    @Column(name = "last_post_comment_at")
    private LocalDateTime lastPostCommentAt;

    public Candidate getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Candidate candidateId) {
        this.candidateId = candidateId;
    }

    public Long getDonationCount() {
        return donationCount;
    }

    public void increaseDonationCount() {
        this.donationCount++;
    }

    public void decreaseDonationCount() {
        this.donationCount--;
    }

    public Long getOfferCount() {
        return offerCount;
    }

    public void increaseOfferCount() {
        this.offerCount++;
    }

    public void decreaseOfferCount() {
        this.offerCount--;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void increaseShareCount() {
        this.shareCount++;
    }

    public void decreaseShareCount() {
        this.shareCount--;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount() {
        this.commentCount--;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public Long getPostCount() {
        return postCount;
    }

    public void increasePostCount() {
        this.postCount++;
    }

    public void decreasePostCount() {
        this.postCount--;
    }

    public Long getPostCommentCount() {
        return postCommentCount;
    }

    public void increasePostCommentCount() {
        this.postCommentCount++;
    }

    public void decreasePostCommentCount() {
        this.postCommentCount--;
    }

    public LocalDateTime getLastDonationAt() {
        return lastDonationAt;
    }

    public void setLastDonationAt(LocalDateTime lastJoinedAt) {
        this.lastDonationAt = lastJoinedAt;
    }

    public LocalDateTime getLastOfferAt() {
        return lastOfferAt;
    }

    public void setLastOfferAt(LocalDateTime lastOfferAt) {
        this.lastOfferAt = lastOfferAt;
    }

    public LocalDateTime getLastShareAt() {
        return lastShareAt;
    }

    public void setLastShareAt(LocalDateTime lastShareAt) {
        this.lastShareAt = lastShareAt;
    }

    public LocalDateTime getLastCommentAt() {
        return lastCommentAt;
    }

    public void setLastCommentAt(LocalDateTime lastCommentAt) {
        this.lastCommentAt = lastCommentAt;
    }

    public LocalDateTime getLastLikeAt() {
        return lastLikeAt;
    }

    public void setLastLikeAt(LocalDateTime lastLikeAt) {
        this.lastLikeAt = lastLikeAt;
    }

    public LocalDateTime getLastPostAt() {
        return lastPostAt;
    }

    public void setLastPostAt(LocalDateTime lastPostAt) {
        this.lastPostAt = lastPostAt;
    }

    public LocalDateTime getLastPostCommentAt() {
        return lastPostCommentAt;
    }

    public void setLastPostCommentAt(LocalDateTime lastPostCommentAt) {
        this.lastPostCommentAt = lastPostCommentAt;
    }
}
