package com.synechisveltiosi.apis.app365.campaign.entity;

import com.synechisveltiosi.apis.app365.citizens.entity.Citizen;
import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.synechisveltiosi.apis.app365.users.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "team_members")
@SqlResultSetMapping(
        name = "TeamLevel",
        classes = @ConstructorResult(
                targetClass = TeamLevel.class,
                columns = {
                        @ColumnResult(name = "_level", type = Integer.class),
                        @ColumnResult(name = "member_count", type = Integer.class)
                }
        )
)
@NamedStoredProcedureQuery(name = "getTeamLevelMembers",
        procedureName = "level_members", resultSetMappings = "TeamLevel",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class, name = "parentId")
        })
public class TeamMember extends BaseEntity {

    private static final long serialVersionUID = 0L;

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "member_id", nullable = false, unique = true)
    private String memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", nullable = false, unique = true)
    private Citizen citizenId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Citizen getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Citizen citizenId) {
        this.citizenId = citizenId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User parentId) {
        this.userId = parentId;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (memberId == null) memberId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
