package com.synechisveltiosi.apis.app365.events.entity;

import com.synechisveltiosi.apis.app365.calendar.Calendar;
import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.synechisveltiosi.apis.app365.location.Location;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;
    public static final List<String> SORTABLE_FIELDS = Arrays.asList(Sortable.TITLE, Sortable.START_DATE);
    public static final List<String> SEARCHABLE_FIELDS = Arrays.asList(Searchable.TITLE, Searchable.START_DATE);
    private static final long serialVersionUID = 0L;
    @Column(name = "event_id")
    private String eventId;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "eventId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @Column(name = "event_id", nullable = false)
    private List<JoinEvent> joinEvents = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "eventId", cascade = CascadeType.ALL, optional = false)
    private EventSummary eventSummary;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "eventId", cascade = CascadeType.ALL, optional = false)
    private Location location;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "eventId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @Column(name = "event_id", nullable = false)
    private List<EventLike> likes;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "eventId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @Column(name = "event_id", nullable = false)
    private List<EventShare> shares;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "eventId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @Column(name = "event_id", nullable = false)
    private List<Calendar> calendars;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "eventId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Basic(optional = false)
    @OrderBy("createdAt DESC")
    @Column(name = "event_id", nullable = false)
    private List<EventComment> comments;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public List<JoinEvent> getJoinEvents() {
        return joinEvents;
    }

    public void setJoinEvents(List<JoinEvent> joinEvents) {
        this.joinEvents = joinEvents;
    }

    public EventSummary getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(EventSummary eventSummary) {
        this.eventSummary = eventSummary;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<EventLike> getLikes() {
        return likes;
    }

    public void setLikes(List<EventLike> likes) {
        this.likes = likes;
    }

    public List<EventShare> getShares() {
        return shares;
    }

    public void setShares(List<EventShare> shares) {
        this.shares = shares;
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    public List<EventComment> getComments() {
        return comments;
    }

    public void setComments(List<EventComment> comments) {
        this.comments = comments;
    }

    public EventComment getLastComment() {
        if (getComments() == null || getComments().isEmpty()) return null;

        return getComments().get(0);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        if (eventId == null) eventId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

    public interface Sortable {
        String TITLE = "title";
        String START_DATE = "startDate";
        String DEFAULT_SORT = "-" + START_DATE;
    }

    public interface Searchable {
        String TITLE = "title";
        String START_DATE = "startDate";
    }
}
