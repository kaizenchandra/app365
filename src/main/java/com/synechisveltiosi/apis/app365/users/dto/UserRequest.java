package com.synechisveltiosi.apis.app365.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {

    @NotBlank(message = "Email address is required.")
    @Size(max = 255, message = "Email address should be 255 characters long.")
    @Email(regexp = "^[A-Z0-9a-z._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$", message = "Invalid email address.")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^[0-9]{4,15}$", message = "Phone number should be 4 to 15 digits long.")
    @JsonProperty("phone")
    private String phone;

    @NotBlank(message = "Phone country code is required.")
    @Pattern(regexp = "^[0-9]{1,5}$", message = "Phone country code should be 1 to 5 digits long.")
    @JsonProperty("phoneCountryCode")
    private String phoneCountryCode;

    @NotBlank(message = "Password is required.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9!#$%&()*+,.;<=>?@^_~]{6,128}$",
            message = "Password should be between 8 to 128 characters. Only letters, numbers and optionally special " +
                    "characters (!#$%&()*+,.;<=>?@^_~) are allowed.")
    @JsonProperty("password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public UserRequest withPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRequest withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRequest withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("phone", phone)
                .append("password", password)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(password)
                .append(phone)
                .append(email)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserRequest)) {
            return false;
        }

        UserRequest rhs = ((UserRequest) other);
        return new EqualsBuilder()
                .append(password, rhs.password)
                .append(phone, rhs.phone)
                .append(email, rhs.email)
                .isEquals();
    }
}
