package com.synechisveltiosi.apis.app365.campaign.dto;

import com.synechisveltiosi.apis.app365.common.dto.places.AddressResponse;
import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamMemberResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("idCard")
    private String idCard;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty(value = "fullName", access = JsonProperty.Access.READ_ONLY)
    private String fullName;

    @JsonProperty("profilePicture")
    private String profilePicture;

    @JsonProperty("birthDate")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date birthDate;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private MilitantRequest.Phone phone;

    @JsonProperty("memberSince")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    @JsonProperty("address")
    private AddressResponse address;

    @JsonProperty("team")
    private TeamMemberSummaryResponse team;

    @JsonProperty("status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TeamMemberResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public TeamMemberResponse withIdCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public TeamMemberResponse withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public TeamMemberResponse withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName() {
        fullName = StringUtils.join(new String[]{firstName, lastName}, " ").trim();
        return StringUtils.isEmpty(fullName) ? null : fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public TeamMemberResponse withProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public TeamMemberResponse withBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        if (birthDate != null && age == null) {
            age = (int) ChronoUnit.YEARS.between(LocalDate.ofEpochDay(birthDate.getTime()), LocalDate.now());
        }

        return age;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TeamMemberResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public TeamMemberResponse withAddress(AddressResponse address) {
        this.address = address;
        return this;
    }

    public TeamMemberSummaryResponse getTeam() {
        return team;
    }

    public void setTeam(TeamMemberSummaryResponse team) {
        this.team = team;
    }

    public TeamMemberResponse withAddress(TeamMemberSummaryResponse team) {
        this.team = team;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MilitantRequest.Phone getPhone() {
        return phone;
    }

    public void setPhone(MilitantRequest.Phone phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("idCard", idCard)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("fullName", fullName)
                .append("profilePicture", profilePicture)
                .append("birthDate", birthDate)
                .append("age", age)
                .append("createdAt", createdAt)
                .append("address", address)
                .append("team", team)
                .append("email", email)
                .append("phone", phone)
                .append("status", status)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TeamMemberResponse that = (TeamMemberResponse) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(idCard, that.idCard)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(fullName, that.fullName)
                .append(profilePicture, that.profilePicture)
                .append(birthDate, that.birthDate)
                .append(age, that.age)
                .append(createdAt, that.createdAt)
                .append(address, that.address)
                .append(team, that.team)
                .append(email, that.email)
                .append(phone, that.phone)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(idCard)
                .append(firstName)
                .append(lastName)
                .append(fullName)
                .append(profilePicture)
                .append(birthDate)
                .append(age)
                .append(createdAt)
                .append(address)
                .append(team)
                .append(email)
                .append(phone)
                .append(status)
                .toHashCode();
    }
}
