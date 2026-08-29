package com.synechisveltiosi.apis.app365.candidates.entity;

import com.synechisveltiosi.apis.app365.accounts.config.SocialTokenConfig;
import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "candidates")
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class Candidate extends BaseEntity {

    @Column(name = "candidate_id")
    private String candidateId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_country_code")
    private String phoneCountryCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "biography_description", columnDefinition = "text")
    private String biographyDescription;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "social_identifier")
    private String socialIdentifier;

    @Column(name = "utc_offset")
    private LocalTime utcOffset;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "language")
    private String language;

    @Column(name = "candidate_for")
    private String candidateFor;

    @Column(name = "active")
    private Boolean active = Boolean.TRUE;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "candidateId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Column(name = "candidate_id", nullable = false)
    private List<CandidateLike> likes;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "candidateId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Column(name = "candidate_id", nullable = false)
    private List<CandidateShare> shares;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "candidateId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @OrderBy("createdAt DESC")
    @Column(name = "candidate_id", nullable = false)
    private List<CandidateComment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "candidateId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @OrderBy("createdAt DESC")
    @Column(name = "candidate_id", nullable = false)
    private List<Post> posts;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "candidateId", cascade = CascadeType.ALL, optional = false)
    private CandidateSummary candidateSummary;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "candidateId", cascade = CascadeType.ALL, optional = false)
    private Party party;

    @Type(type = "jsonb")
    @Column(name = "social_tokens", columnDefinition = "json")
    private List<SocialTokenConfig> socialTokens;

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneCountryCode() {
        if (StringUtils.isNotBlank(phoneCountryCode)) {
            phoneCountryCode = phoneCountryCode.replaceAll("[^0-9]", "");
        }

        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        if (StringUtils.isNotBlank(phoneCountryCode)) {
            phoneCountryCode = phoneCountryCode.replaceAll("[^0-9]", "");
        }

        this.phoneCountryCode = phoneCountryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBiographyDescription() {
        return biographyDescription;
    }

    public void setBiographyDescription(String biographyDescription) {
        this.biographyDescription = biographyDescription;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSocialIdentifier() {
        return socialIdentifier;
    }

    public void setSocialIdentifier(String socialIdentifier) {
        this.socialIdentifier = socialIdentifier;
    }

    public LocalTime getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(LocalTime utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCandidateFor() {
        return candidateFor;
    }

    public void setCandidateFor(String candidateFor) {
        this.candidateFor = candidateFor;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public List<CandidateLike> getLikes() {
        return likes;
    }

    public void setLikes(List<CandidateLike> likes) {
        this.likes = likes;
    }

    public List<CandidateShare> getShares() {
        return shares;
    }

    public void setShares(List<CandidateShare> shares) {
        this.shares = shares;
    }

    public List<CandidateComment> getComments() {
        return comments;
    }

    public void setComments(List<CandidateComment> comments) {
        this.comments = comments;
    }

    public CandidateComment getLastComment() {
        if (getComments() == null || getComments().isEmpty()) return null;

        return getComments().get(0);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public CandidateSummary getCandidateSummary() {
        return candidateSummary;
    }

    public void setCandidateSummary(CandidateSummary candidateSummary) {
        this.candidateSummary = candidateSummary;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public List<SocialTokenConfig> getSocialTokens() {
        return socialTokens;
    }

    public void setSocialTokens(List<SocialTokenConfig> socialTokens) {
        this.socialTokens = socialTokens;
    }

    @PrePersist
    public void prePersist() {
        if (candidateId == null) candidateId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Candidate candidate = (Candidate) o;

        return new EqualsBuilder()
                .append(candidateId, candidate.candidateId)
                .append(firstName, candidate.firstName)
                .append(lastName, candidate.lastName)
                .append(idCard, candidate.idCard)
                .append(email, candidate.email)
                .append(phone, candidate.phone)
                .append(birthDate, candidate.birthDate)
                .append(biographyDescription, candidate.biographyDescription)
                .append(profilePicture, candidate.profilePicture)
                .append(socialIdentifier, candidate.socialIdentifier)
                .append(utcOffset, candidate.utcOffset)
                .append(timeZone, candidate.timeZone)
                .append(language, candidate.language)
                .append(candidateFor, candidate.candidateFor)
                .append(active, candidate.active)
                .append(createdAt, candidate.createdAt)
                .append(updatedAt, candidate.updatedAt)
                .append(deletedAt, candidate.deletedAt)
                .append(likes, candidate.likes)
                .append(shares, candidate.shares)
                .append(comments, candidate.comments)
                .append(posts, candidate.posts)
                .append(candidateSummary, candidate.candidateSummary)
                .append(party, candidate.party)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(candidateId)
                .append(firstName)
                .append(lastName)
                .append(idCard)
                .append(email)
                .append(phone)
                .append(birthDate)
                .append(biographyDescription)
                .append(profilePicture)
                .append(socialIdentifier)
                .append(utcOffset)
                .append(timeZone)
                .append(language)
                .append(candidateFor)
                .append(active)
                .append(createdAt)
                .append(updatedAt)
                .append(deletedAt)
                .append(likes)
                .append(shares)
                .append(comments)
                .append(posts)
                .append(candidateSummary)
                .append(party)
                .toHashCode();
    }
}
