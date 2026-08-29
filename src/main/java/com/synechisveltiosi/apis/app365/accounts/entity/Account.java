package com.synechisveltiosi.apis.app365.accounts.entity;

import com.synechisveltiosi.apis.app365.accounts.AccountStatus;
import com.synechisveltiosi.apis.app365.accounts.config.AccountConfiguration;
import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class Account extends BaseEntity {

    private static final long serialVersionUID = 0L;

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;

    @Column(name = "account_id", nullable = false, unique = true)
    private String accountId;

    @Column(name = "subdomain", nullable = false, unique = true)
    private String subdomain;

    @Column(name = "verified")
    private Boolean verified = Boolean.FALSE;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Type(type = "json")
    @Column(name = "configurations", columnDefinition = "json")
    private final AccountConfiguration configuration = new AccountConfiguration();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "accountId", cascade = CascadeType.ALL, optional = false)
    private AccountCandidate candidateId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public Boolean isVerified() {
        return verified != null && verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public AccountConfiguration getConfiguration() {
        return configuration;
    }

    public AccountCandidate getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(AccountCandidate candidateId) {
        if (candidateId != null) candidateId.setAccountId(this);

        this.candidateId = candidateId;
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

    @PrePersist
    public void prePersist() {
        if (accountId == null) accountId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
