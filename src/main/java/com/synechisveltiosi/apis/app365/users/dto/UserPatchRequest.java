package com.synechisveltiosi.apis.app365.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.dto.places.AddressRequest;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPatchRequest {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("idCard")
    private String idCard;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private AddressRequest address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserPatchRequest withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserPatchRequest withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserPatchRequest withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public UserPatchRequest withIdCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserPatchRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserPatchRequest withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }

    public UserPatchRequest withAddress(AddressRequest address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("nickname", nickname)
                .append("idCard", idCard)
                .append("email", email)
                .append("phone", phone)
                .append("address", address)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(firstName)
                .append(lastName)
                .append(address)
                .append(phone)
                .append(idCard)
                .append(nickname)
                .append(email)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserPatchRequest)) {
            return false;
        }

        UserPatchRequest rhs = ((UserPatchRequest) other);
        return new EqualsBuilder()
                .append(firstName, rhs.firstName)
                .append(lastName, rhs.lastName)
                .append(address, rhs.address)
                .append(phone, rhs.phone)
                .append(idCard, rhs.idCard)
                .append(nickname, rhs.nickname)
                .append(email, rhs.email)
                .isEquals();
    }
}
