package com.synechisveltiosi.apis.app365.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequest {

    @NotBlank(message = "Site name is required")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Site name can only contains alphanumeric characters.")
    @Size(min = 6, max = 63, message = "Subdomain should be 6 to 63 characters. ")
    @JsonProperty("subdomain")
    private String subdomain;

    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 50, message = "First name should be between 2 to 50 characters.")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "First name can only contain alpha and space characters.")
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 50, message = "Last name should be between 2 to 50 characters.")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Last name can only contain alpha and space characters.")
    @JsonProperty("lastName")
    private String lastName;

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

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }
}
