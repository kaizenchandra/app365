package com.synechisveltiosi.apis.app365.candidates.entity;

import com.synechisveltiosi.apis.app365.users.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_users")
public class CandidateUser implements Serializable {

    private static final long serialVersionUID = 0L;

    @Id
    private CandidateUserPk id = new CandidateUserPk();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public CandidateUser() {

    }

    public CandidateUser(User user) {
        id.setUserId(user);
    }

    public CandidateUser(Candidate candidate) {
        id.setCandidateId(candidate);
    }

    public CandidateUser(Candidate candidate, User user) {
        id.setCandidateId(candidate);
        id.setUserId(user);
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
