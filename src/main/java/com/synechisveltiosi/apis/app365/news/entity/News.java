package com.synechisveltiosi.apis.app365.news.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "news")
public class News extends BaseEntity {

    private static final long serialVersionUID = 0L;

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;
    public static final List<String> SORTABLE_FIELDS = Arrays.asList(Sortable.TITLE, Sortable.CREATED_AT);
    public static final List<String> SEARCHABLE_FIELDS = Arrays.asList(Searchable.TITLE, Searchable.CREATED_AT);

    @Column(name = "news_id", nullable = false, unique = true)
    private String newsId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "cover_picture")
    private String coverPicture;

    @Column(name = "is_flagged")
    private Boolean flagged = Boolean.FALSE;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "newsId", cascade = CascadeType.ALL, optional = false)
    private NewsSummary newsSummary;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "newsId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @Column(name = "news_id", nullable = false)
    private List<NewsLike> likes;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "newsId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @Column(name = "news_id", nullable = false)
    private List<NewsShare> shares;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "newsId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @OrderBy("createdAt DESC")
    @Column(name = "news_id", nullable = false)
    private List<NewsComment> comments;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
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

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public Boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(Boolean flagged) {
        this.flagged = flagged;
    }

    public NewsSummary getNewsSummary() {
        return newsSummary;
    }

    public void setNewsSummary(NewsSummary newsSummary) {
        this.newsSummary = newsSummary;
    }

    public List<NewsLike> getLikes() {
        return likes;
    }

    public void setLikes(List<NewsLike> likes) {
        this.likes = likes;
    }

    public List<NewsShare> getShares() {
        return shares;
    }

    public void setShares(List<NewsShare> shares) {
        this.shares = shares;
    }

    public List<NewsComment> getComments() {
        return comments;
    }

    public NewsComment getLastComment() {
        if (getComments() == null || getComments().isEmpty()) return null;

        return getComments().get(0);
    }

    public void setComments(List<NewsComment> comments) {
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
        if (newsId == null) newsId = UUID.randomUUID().toString();
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
