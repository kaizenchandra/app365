package com.synechisveltiosi.apis.app365.notifications;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class Notification extends BaseEntity {

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;
    public static final String NOTIFICATION_EVENT_PUBLISHED_JOIN = "event.published.join";
    public static final String NOTIFICATION_ARTICLE_PUBLISHED = "article.published";
    public static final String NOTIFICATION_ARTICLES_PUBLISHED = "articles.published";
    public static final String NOTIFICATION_LIKE = "like";
    public static final String NOTIFICATION_INFO_PLAIN = "info.plain";
    public static final String NOTIFICATION_INFO_HTML = "info.html";
    public static final String NOTIFICATION_USER_MEDALS_LOST = "user.medals.lost";
    public static final String NOTIFICATION_USER_MEDALS_AWARDED = "user.medals.awarded";
    private static final long serialVersionUID = 0L;
    @Column(name = "notification_id", unique = true)
    private String notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "target")
    @Enumerated(EnumType.STRING)
    private NotificationTarget target;

    @Column(name = "type", nullable = false)
    private String type;

    @Type(type = "jsonb")
    @Column(name = "extras", columnDefinition = "json")
    private Object extras;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
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

    public NotificationTarget getTarget() {
        return target;
    }

    public void setTarget(NotificationTarget target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (notificationId == null) notificationId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("notificationId", notificationId)
                .append("userId", userId)
                .append("title", title)
                .append("content", content)
                .append("target", target)
                .append("type", type)
                .append("extras", extras)
                .append("createdAt", createdAt)
                .toString();
    }
}
