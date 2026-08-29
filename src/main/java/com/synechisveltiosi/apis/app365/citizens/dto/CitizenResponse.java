
package com.synechisveltiosi.apis.app365.citizens.dto;

import com.synechisveltiosi.apis.app365.common.dto.places.FlatAddressResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CitizenResponse {

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

    @JsonProperty("address")
    private FlatAddressResponse address;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public CitizenResponse withIdCard(String idCard) {
        this.idCard = idCard;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public CitizenResponse withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CitizenResponse withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName() {
        fullName = StringUtils.join(new String[]{firstName, lastName}, " ");
        return fullName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public CitizenResponse withProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public FlatAddressResponse getAddress() {
        return address;
    }

    public void setAddress(FlatAddressResponse address) {
        this.address = address;
    }

    public CitizenResponse withAddress(FlatAddressResponse address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("idCard", idCard)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("fullName", fullName)
                .append("profilePicture", profilePicture)
                .append("fullAddress", address)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(firstName)
                .append(lastName)
                .append(profilePicture)
                .append(address)
                .append(idCard)
                .append(fullName)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CitizenResponse)) {
            return false;
        }

        CitizenResponse rhs = ((CitizenResponse) other);
        return new EqualsBuilder()
                .append(firstName, rhs.firstName)
                .append(lastName, rhs.lastName)
                .append(profilePicture, rhs.profilePicture)
                .append(address, rhs.address)
                .append(idCard, rhs.idCard)
                .append(fullName, rhs.fullName)
                .isEquals();
    }
}
