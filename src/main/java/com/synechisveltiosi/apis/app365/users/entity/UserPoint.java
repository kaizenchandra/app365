package com.synechisveltiosi.apis.app365.users.entity;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_points")
public class UserPoint implements Serializable {

    private static final long serialVersionUID = 0L;

    @Id
    private UserPointPk id = new UserPointPk();

    @Column(name = "points")
    private Long points = 0L;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public UserPoint() {

    }

    public UserPoint(User user) {
        id.setUserId(user);
    }

    public UserPoint(ActionType actionType) {
        id.setActionTypeName(actionType);
    }

    public UserPoint(User user, ActionType actionType) {
        id.setUserId(user);
        id.setActionTypeName(actionType);
    }

    public UserPointPk getId() {
        return id;
    }

    public void setId(UserPointPk id) {
        this.id = id;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
