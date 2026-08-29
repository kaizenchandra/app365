package com.synechisveltiosi.apis.app365.users.entity;

import com.synechisveltiosi.apis.app365.actions.entity.Medal;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "awards")
public class Award implements Serializable {

    private static final long serialVersionUID = 0L;

    @Id
    private AwardPk id = new AwardPk();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Award() {

    }

    public Award(User user) {
        id.setUserId(user);
    }

    public Award(Medal medal) {
        id.setMedalId(medal);
    }

    public Award(User user, Medal medal) {
        id.setUserId(user);
        id.setMedalId(medal);
    }

    public AwardPk getId() {
        return id;
    }

    public void setId(AwardPk id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
