package com.synechisveltiosi.apis.app365.campaign.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Alfredo Martinez <martin3zra@gmail.com> on 10/20/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamMemberRequest {

    @NotBlank(message = "Militant email is required")
    @Size(max = 255, message = "Email address should be 255 characters long.")
    @Email(regexp = "^[A-Z0-9a-z._-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$", message = "Invalid email address.")
    @JsonProperty("email")
    private String email;
    @NotNull(message = "Militant phone is required")
    @JsonProperty("phone")
    private MilitantRequest.Phone phone;

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
}
