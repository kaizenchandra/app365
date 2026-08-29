package com.synechisveltiosi.apis.app365.campaign.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.synechisveltiosi.apis.app365.users.entity.User;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "volunteers")
public class Volunteer extends BaseEntity {

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;
    public static final List<String> SORTABLE_FIELDS = Arrays.asList(Sortable.FIRST_NAME, Sortable.LAST_NAME,
            Sortable.CREATED_AT);
    public static final List<String> SEARCHABLE_FIELDS = Arrays.asList(Searchable.FIRST_NAME, Searchable.LAST_NAME,
            Searchable.ID_CARD, Searchable.EMAIL, Searchable.PHONE);

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at")
    private Date createdAt;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = new Date();
    }

    // TODO Sort on inner fields
    public interface Sortable {
        String FIRST_NAME = "userId.firstName";
        String LAST_NAME = "userId.lastName";
        String CREATED_AT = "createdAt";
        //        String DEFAULT_SORT = "+" + FIRST_NAME + ",+" + LAST_NAME;
        String DEFAULT_SORT = "-" + CREATED_AT;
    }

    // TODO Search on inner fields
    public interface Searchable {
        String FIRST_NAME = "userId.firstName";
        String LAST_NAME = "userId.lastName";
        String ID_CARD = "userId.idCard";
        String PHONE = "userId.phone";
        String EMAIL = "userId.email";
    }
}
