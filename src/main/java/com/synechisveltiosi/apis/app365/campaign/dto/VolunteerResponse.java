package com.synechisveltiosi.apis.app365.campaign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VolunteerResponse {

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

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VolunteerResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public VolunteerResponse withIdCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public VolunteerResponse withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public VolunteerResponse withLastName(String lastName) {
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

    public VolunteerResponse withProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public VolunteerResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public VolunteerResponse withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public VolunteerResponse withEmail(String email) {
        this.email = email;
        return this;
    }
}
