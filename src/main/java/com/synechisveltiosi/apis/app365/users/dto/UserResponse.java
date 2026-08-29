package com.synechisveltiosi.apis.app365.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.actions.dto.AwardsResponse;
import com.synechisveltiosi.apis.app365.common.dto.places.AddressResponse;
import com.synechisveltiosi.apis.app365.common.util.RegistrationChannel;
import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty(value = "fullName", access = JsonProperty.Access.READ_ONLY)
    private String fullName;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("idCard")
    private String idCard;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phoneCountryCode")
    private String phoneCountryCode;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("profilePicture")
    private String profilePicture;

    @JsonProperty("birthDate")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date birthDate;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("channel")
    private RegistrationChannel channel;

    @JsonProperty("verified")
    private Boolean verified;

    @JsonProperty("timeZoneOffset")
    private Integer timeZoneOffset;

    @JsonProperty("timeZone")
    private String timeZone;

    @JsonProperty("language")
    private String language;

    @JsonProperty("meta")
    private UserMetaDataResponse metaData;

    @JsonProperty("awards")
    private AwardsResponse awards;

    @JsonProperty("address")
    private AddressResponse address;

    @JsonProperty("cbaHeader")
    private CbaHeaderResponse cbaHeader;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserResponse withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserResponse withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName() {
        fullName = StringUtils.join(new String[]{firstName, lastName}, " ").trim();
        return StringUtils.isEmpty(fullName) ? null : fullName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserResponse withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public UserResponse withIdCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserResponse withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public UserResponse withPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserResponse withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UserResponse withProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public UserResponse withBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public Integer getAge() {
        if (birthDate != null) {
            age = (int) ChronoUnit.YEARS.between(LocalDate.ofEpochDay(birthDate.getTime()), LocalDate.now());
        }

        return age;
    }

    public RegistrationChannel getChannel() {
        return channel;
    }

    public void setChannel(RegistrationChannel channel) {
        this.channel = channel;
    }

    public UserResponse withChannel(RegistrationChannel channel) {
        this.channel = channel;
        return this;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public UserResponse withVerified(Boolean verified) {
        this.verified = verified;
        return this;
    }

    public Integer getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(Integer timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public UserResponse withUtcOffset(Integer utcOffset) {
        this.timeZoneOffset = utcOffset;
        return this;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public UserResponse withTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public UserResponse withLanguage(String language) {
        this.language = language;
        return this;
    }

    public UserMetaDataResponse getMetaData() {
        return metaData;
    }

    public void setMetaData(UserMetaDataResponse metaData) {
        this.metaData = metaData;
    }

    public UserResponse withMetaData(UserMetaDataResponse metaData) {
        this.metaData = metaData;
        return this;
    }

    public AwardsResponse getAwards() {
        return awards;
    }

    public void setAwards(AwardsResponse awards) {
        this.awards = awards;
    }

    public UserResponse withAwards(AwardsResponse awards) {
        this.awards = awards;
        return this;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public UserResponse withAddress(AddressResponse address) {
        this.address = address;
        return this;
    }

    public CbaHeaderResponse getCbaHeader() {
        return cbaHeader;
    }

    public void setCbaHeader(CbaHeaderResponse cbaHeader) {
        this.cbaHeader = cbaHeader;
    }

    public UserResponse withCbaHeader(CbaHeaderResponse cbaHeader) {
        this.cbaHeader = cbaHeader;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("fullName", fullName)
                .append("nickname", nickname)
                .append("idCard", idCard)
                .append("email", email)
                .append("phoneCountryCode", phoneCountryCode)
                .append("phone", phone)
                .append("profilePicture", profilePicture)
                .append("birthDate", birthDate)
                .append("age", age)
                .append("channel", channel)
                .append("verified", verified)
                .append("timeZoneOffset", timeZoneOffset)
                .append("timeZone", timeZone)
                .append("language", language)
                .append("metaData", metaData)
                .append("awards", awards)
                .append("address", address)
                .append("createdAt", createdAt)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserResponse that = (UserResponse) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(fullName, that.fullName)
                .append(nickname, that.nickname)
                .append(idCard, that.idCard)
                .append(email, that.email)
                .append(phoneCountryCode, that.phoneCountryCode)
                .append(phone, that.phone)
                .append(profilePicture, that.profilePicture)
                .append(birthDate, that.birthDate)
                .append(age, that.age)
                .append(channel, that.channel)
                .append(verified, that.verified)
                .append(timeZoneOffset, that.timeZoneOffset)
                .append(timeZone, that.timeZone)
                .append(language, that.language)
                .append(metaData, that.metaData)
                .append(awards, that.awards)
                .append(address, that.address)
                .append(createdAt, that.createdAt)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(firstName)
                .append(lastName)
                .append(fullName)
                .append(nickname)
                .append(idCard)
                .append(email)
                .append(phoneCountryCode)
                .append(phone)
                .append(profilePicture)
                .append(birthDate)
                .append(age)
                .append(channel)
                .append(verified)
                .append(timeZoneOffset)
                .append(timeZone)
                .append(language)
                .append(metaData)
                .append(awards)
                .append(address)
                .append(createdAt)
                .toHashCode();
    }
}
