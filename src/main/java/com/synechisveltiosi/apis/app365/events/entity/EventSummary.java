package com.synechisveltiosi.apis.app365.events.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event_summary")
public class EventSummary extends BaseEntity {

    private static final long serialVersionUID = 0L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false, unique = true)
    private Event eventId;

    @Column(name = "join_count")
    private Long joinCount = 0L;

    @Column(name = "share_count")
    private Long shareCount = 0L;

    @Column(name = "comment_count")
    private Long commentCount = 0L;

    @Column(name = "like_count")
    private Long likeCount = 0L;

    @Column(name = "last_joined_at")
    private Date lastJoinedAt;

    @Column(name = "last_share_at")
    private Date lastShareAt;

    @Column(name = "last_comment_at")
    private Date lastCommentAt;

    @Column(name = "last_like_at")
    private Date lastLikeAt;

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public Long getJoinCount() {
        return joinCount;
    }

    public void increaseJoinCount() {
        this.joinCount++;
    }

    public void decreaseJoinCount() {
        this.joinCount--;
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

    public Date getLastJoinedAt() {
        return lastJoinedAt;
    }

    public void setLastJoinedAt(Date lastJoinedAt) {
        this.lastJoinedAt = lastJoinedAt;
    }

    public Date getLastShareAt() {
        return lastShareAt;
    }

    public void setLastShareAt(Date lastShareAt) {
        this.lastShareAt = lastShareAt;
    }

    public Date getLastCommentAt() {
        return lastCommentAt;
    }

    public void setLastCommentAt(Date lastCommentAt) {
        this.lastCommentAt = lastCommentAt;
    }

    public Date getLastLikeAt() {
        return lastLikeAt;
    }

    public void setLastLikeAt(Date lastLikeAt) {
        this.lastLikeAt = lastLikeAt;
    }
}
