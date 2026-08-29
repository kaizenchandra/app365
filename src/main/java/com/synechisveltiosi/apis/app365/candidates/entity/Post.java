package com.synechisveltiosi.apis.app365.candidates.entity;

import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;
    public static final List<String> SORTABLE_FIELDS = Collections.singletonList(Sortable.CREATED_AT);

    @Column(name = "post_id")
    private String postId;

    @Column(name = "external_post_id", unique = true)
    private String externalPostId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidateId;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "channel")
    @Enumerated(EnumType.STRING)
    private SocialNetworkProvider channel;

    @Column(name = "external_created_at")
    private LocalDateTime externalCreatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "postId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @OrderBy("createdAt DESC")
    @Column(name = "post_id", nullable = false)
    private List<PostComment> comments;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getExternalPostId() {
        return externalPostId;
    }

    public void setExternalPostId(String externalPostId) {
        this.externalPostId = externalPostId;
    }

    public Candidate getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Candidate candidateId) {
        this.candidateId = candidateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SocialNetworkProvider getChannel() {
        return channel;
    }

    public void setChannel(SocialNetworkProvider channel) {
        this.channel = channel;
    }

    public LocalDateTime getExternalCreatedAt() {
        return externalCreatedAt;
    }

    public void setExternalCreatedAt(LocalDateTime externalCreatedAt) {
        this.externalCreatedAt = externalCreatedAt;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }

    @PrePersist
    public void prePersist() {
        if (postId == null) postId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public interface Sortable {
        String CREATED_AT = "createdAt";
        String DEFAULT_SORT = "-" + CREATED_AT;
    }
}
