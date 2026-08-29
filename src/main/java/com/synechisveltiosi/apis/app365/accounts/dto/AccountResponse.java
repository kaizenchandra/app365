package com.synechisveltiosi.apis.app365.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("candidateId")
    private String candidateId;

    @JsonProperty("subdomain")
    private String subdomain;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("phoneCountryCode")
    private String phoneCountryCode;

    @JsonProperty("candidateFor")
    private String candidateFor;

    @JsonProperty("configuration")
    private AccountConfigurationResponse configuration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

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

    public String getCandidateFor() {
        return candidateFor;
    }

    public void setCandidateFor(String candidateFor) {
        this.candidateFor = candidateFor;
    }

    public AccountConfigurationResponse getConfiguration() {
        return configuration;
    }

    public void setConfiguration(AccountConfigurationResponse configuration) {
        this.configuration = configuration;
    }
}
