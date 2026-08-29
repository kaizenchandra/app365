package com.synechisveltiosi.apis.app365.users.entity;

import com.synechisveltiosi.apis.app365.address.entity.Address;
import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.synechisveltiosi.apis.app365.common.util.RegistrationChannel;
import com.synechisveltiosi.apis.app365.users.UserMetaData;
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
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
public class User extends BaseEntity {

    public static final Integer DEFAULT_PAGE = 0;
    public static final Integer MAX_PAGE_SIZE = 25;
    public static final List<String> SORTABLE_FIELDS = Arrays.asList(Sortable.FIRST_NAME, Sortable.LAST_NAME,
            Sortable.EMAIL, Sortable.CREATED_AT);
    public static final List<String> SEARCHABLE_FIELDS = Arrays.asList(Searchable.FIRST_NAME, Searchable.LAST_NAME,
            Searchable.EMAIL);
    public static final List<String> PATCHABLE_FIELDS = Arrays.asList(Patchable.FIRST_NAME, Patchable.LAST_NAME,
            Patchable.NICKNAME, Patchable.ID_CARD, Patchable.EMAIL, Patchable.PHONE, Patchable.ADDRESS);
    private static final long serialVersionUID = 0L;
    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "auth_user_id", unique = true)
    private Long authUserId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_country_code")
    private String phoneCountryCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "phone_verified")
    private Boolean phoneVerified = Boolean.FALSE;

    @Column(name = "phone_verification_code")
    private String phoneVerificationCode;

    @Column(name = "emitted_phone_code_count")
    private Integer emittedPhoneCodeCount = 0;

    @Column(name = "last_phone_verification_code_sent_at")
    private Date lastPhoneVerificationCodeSentAt;

    @Column(name = "email_verified")
    private Boolean emailVerified = Boolean.FALSE;

    @Column(name = "email_verification_code")
    private String emailVerificationCode;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "birth_date")
    private Date birthDate;

    @Transient
    @Column(name = "password")
    private String password;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "channel")
    @Enumerated(EnumType.STRING)
    private RegistrationChannel channel;

    @Column(name = "social_identifier")
    private String socialIdentifier;

    @Type(type = "json")
    @Column(name = "meta", columnDefinition = "json")
    private UserMetaData metaData;

    @Type(type = "json")
    @Column(name = "settings", columnDefinition = "json")
    private Map<String, Object> settings;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userId", cascade = CascadeType.ALL, optional = false)
    private Address address;

    @Column(name = "time_zone_offset")
    private Integer timeZoneOffset;

    @Column(name = "time_zone")
    private String timeZone;

    @Column(name = "language")
    private String language;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "id.userId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Column(name = "user_id", nullable = false)
    private List<Award> awards;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "id.userId")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @Column(name = "user_id", nullable = false)
    private List<UserPoint> userPoints;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "verified")
    private Boolean verified = Boolean.FALSE;

    @Column(name = "active")
    private Boolean active = Boolean.FALSE;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(Long authUserId) {
        this.authUserId = authUserId;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFullName() {
        String fullName = StringUtils.join(new String[]{firstName, lastName}, " ").trim();
        return StringUtils.isEmpty(fullName) ? null : fullName;
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

    public Date getLastPhoneVerificationCodeSentAt() {
        return lastPhoneVerificationCodeSentAt;
    }

    public void setLastPhoneVerificationCodeSentAt(Date lastPhoneVerificationCodeSentAt) {
        this.lastPhoneVerificationCodeSentAt = lastPhoneVerificationCodeSentAt;
    }

    public Boolean isEmailVerified() {
        return (emailVerified != null && emailVerified) ? Boolean.TRUE : Boolean.FALSE;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmailVerificationCode() {
        return emailVerificationCode;
    }

    public void setEmailVerificationCode(String emailVerificationCode) {
        this.emailVerificationCode = emailVerificationCode;
    }

    public LocalDateTime getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(LocalDateTime emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public RegistrationChannel getChannel() {
        return channel;
    }

    public void setChannel(RegistrationChannel channel) {
        this.channel = channel;
    }

    public String getSocialIdentifier() {
        return socialIdentifier;
    }

    public void setSocialIdentifier(String socialIdentifier) {
        this.socialIdentifier = socialIdentifier;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(Boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public String getPhoneVerificationCode() {
        return phoneVerificationCode;
    }

    public void setPhoneVerificationCode(String phoneVerificationCode) {
        this.phoneVerificationCode = phoneVerificationCode;
    }

    public Integer getEmittedPhoneCodeCount() {
        return emittedPhoneCodeCount;
    }

    public void resetEmittedPhoneCodeCount() {
        this.emittedPhoneCodeCount = 0;
    }

    public void incrementEmittedPhoneCodeCount() {
        if (this.emittedPhoneCodeCount == null)
            this.emittedPhoneCodeCount = 0;

        this.emittedPhoneCodeCount++;
    }

    public UserMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(UserMetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, Object> getSettings() {
        return settings != null ? settings : new HashMap<>();
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(Integer utcOffset) {
        this.timeZoneOffset = utcOffset;
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

    public List<Award> getAwards() {
        return awards;
    }

    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }

    public List<UserPoint> getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(List<UserPoint> userPoints) {
        this.userPoints = userPoints;
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
        if (userId == null) userId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(userId, user.userId)
                .append(authUserId, user.authUserId)
                .append(firstName, user.firstName)
                .append(lastName, user.lastName)
                .append(nickname, user.nickname)
                .append(email, user.email)
                .append(phoneCountryCode, user.phoneCountryCode)
                .append(phone, user.phone)
                .append(phoneVerified, user.phoneVerified)
                .append(emittedPhoneCodeCount, user.emittedPhoneCodeCount)
                .append(lastPhoneVerificationCodeSentAt, user.lastPhoneVerificationCodeSentAt)
                .append(idCard, user.idCard)
                .append(birthDate, user.birthDate)
                .append(password, user.password)
                .append(profilePicture, user.profilePicture)
                .append(channel, user.channel)
                .append(socialIdentifier, user.socialIdentifier)
                .append(metaData, user.metaData)
                .append(settings, user.settings)
                .append(timeZoneOffset, user.timeZoneOffset)
                .append(timeZone, user.timeZone)
                .append(language, user.language)
                .append(createdAt, user.createdAt)
                .append(updatedAt, user.updatedAt)
                .append(deletedAt, user.deletedAt)
                .append(verified, user.verified)
                .append(active, user.active)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userId)
                .append(authUserId)
                .append(firstName)
                .append(lastName)
                .append(nickname)
                .append(email)
                .append(phoneCountryCode)
                .append(phone)
                .append(phoneVerified)
                .append(emittedPhoneCodeCount)
                .append(lastPhoneVerificationCodeSentAt)
                .append(idCard)
                .append(birthDate)
                .append(password)
                .append(profilePicture)
                .append(channel)
                .append(socialIdentifier)
                .append(metaData)
                .append(settings)
                .append(timeZoneOffset)
                .append(timeZone)
                .append(language)
                .append(createdAt)
                .append(updatedAt)
                .append(deletedAt)
                .append(verified)
                .append(active)
                .toHashCode();
    }

    public interface Sortable {
        String FIRST_NAME = "firstName";
        String LAST_NAME = "lastName";
        String EMAIL = "email";
        String CREATED_AT = "createdAt";
        String DEFAULT_SORT = "+" + FIRST_NAME + ",+" + LAST_NAME;
    }

    public interface Searchable {
        String FIRST_NAME = "firstName";
        String LAST_NAME = "lastName";
        String EMAIL = "email";
    }

    public interface Patchable {
        String FIRST_NAME = "firstName";
        String LAST_NAME = "lastName";
        String NICKNAME = "nickname";
        String ID_CARD = "idCard";
        String EMAIL = "email";
        String PHONE = "phone";
        String ADDRESS = "address";
    }
}
