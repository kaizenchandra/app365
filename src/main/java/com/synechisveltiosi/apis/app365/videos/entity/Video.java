package com.synechisveltiosi.apis.app365.videos.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "videos")
public class Video extends BaseEntity {

    private static final long serialVersionUID = 0L;

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;
    public static final List<String> SORTABLE_FIELDS = Arrays.asList(Sortable.TITLE, Sortable.CREATED_AT);
    public static final List<String> SEARCHABLE_FIELDS = Arrays.asList(Searchable.TITLE, Searchable.CREATED_AT);

    @Column(name = "video_id", nullable = false, unique = true)
    private String videoId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "url", nullable = false)
    private String url;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "videoId", cascade = CascadeType.ALL, optional = false)
    private VideoSummary videoSummary;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "videoId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @Column(name = "video_id", nullable = false)
    private List<VideoLike> likes;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "videoId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @Column(name = "video_id", nullable = false)
    private List<VideoShare> shares;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "videoId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @OrderBy("createdAt DESC")
    @Column(name = "video_id", nullable = false)
    private List<VideoComment> comments;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public VideoSummary getVideoSummary() {
        return videoSummary;
    }

    public void setVideoSummary(VideoSummary videoSummary) {
        this.videoSummary = videoSummary;
    }

    public List<VideoLike> getLikes() {
        return likes;
    }

    public void setLikes(List<VideoLike> likes) {
        this.likes = likes;
    }

    public List<VideoShare> getShares() {
        return shares;
    }

    public void setShares(List<VideoShare> shares) {
        this.shares = shares;
    }

    public List<VideoComment> getComments() {
        return comments;
    }

    public VideoComment getLastComment() {
        if (getComments() == null || getComments().isEmpty()) return null;

        return getComments().get(0);
    }

    public void setComments(List<VideoComment> comments) {
        this.comments = comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @PrePersist
    public void prePersist() {
        if (videoId == null) videoId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

    public interface Sortable {
        String TITLE = "title";
        String CREATED_AT = "createdAt";
        String DEFAULT_SORT = "-" + CREATED_AT;
    }

    public interface Searchable {
        String TITLE = "title";
        String CREATED_AT = "createdAt";
    }
}
