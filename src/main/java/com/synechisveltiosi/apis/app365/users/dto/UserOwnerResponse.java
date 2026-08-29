
package com.synechisveltiosi.apis.app365.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOwnerResponse {

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

    @JsonProperty("profilePicture")
    private String profilePicture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserOwnerResponse withId(String id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserOwnerResponse withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserOwnerResponse withLastName(String lastName) {
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

    public UserOwnerResponse withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UserOwnerResponse withProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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
                .append("profilePicture", profilePicture)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(lastName)
                .append(fullName)
                .append(firstName)
                .append(nickname)
                .append(id)
                .append(profilePicture)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserOwnerResponse)) {
            return false;
        }

        UserOwnerResponse rhs = ((UserOwnerResponse) other);
        return new EqualsBuilder()
                .append(lastName, rhs.lastName)
                .append(fullName, rhs.fullName)
                .append(firstName, rhs.firstName)
                .append(nickname, rhs.nickname)
                .append(id, rhs.id)
                .append(profilePicture, rhs.profilePicture)
                .isEquals();
    }
}
