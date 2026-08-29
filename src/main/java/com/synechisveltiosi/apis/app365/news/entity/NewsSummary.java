package com.synechisveltiosi.apis.app365.news.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "news_summary")
public class NewsSummary extends BaseEntity {

    private static final long serialVersionUID = 0L;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false, unique = true)
    private News newsId;

    @Column(name = "share_count")
    private Long shareCount = 0L;

    @Column(name = "comment_count")
    private Long commentCount = 0L;

    @Column(name = "like_count")
    private Long likeCount = 0L;

    @Column(name = "last_share_at")
    private Date lastShareAt;

    @Column(name = "last_comment_at")
    private Date lastCommentAt;

    @Column(name = "last_like_at")
    private Date lastLikeAt;

    public News getNewsId() {
        return newsId;
    }

    public void setNewsId(News newsId) {
        this.newsId = newsId;
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
