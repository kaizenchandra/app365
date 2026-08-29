package com.synechisveltiosi.apis.app365.campaign.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by Alfredo Martinez <martin3zra@gmail.com> on 10/20/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MilitantRequest {

    @NotBlank(message = "Militant id is required")
    @JsonProperty("id")
    private String id;

    @JsonProperty("idCard")
    private String idCard;

    @JsonProperty("email")
    private String email;

    @NotNull(message = "Militant phone is required")
    @JsonProperty("phone")
    private Phone phone;

    @JsonProperty("associated")
    private Associated associated;

    @NotNull(message = "Militant address is required")
    @JsonProperty("address")
    private Map<String, Object> address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Phone {

        @JsonProperty("homeCountryCode")
        private String homeCountryCode;

        @JsonProperty("home")
        private String home;

        @JsonProperty("mobileCountryCode")
        private String mobileCountryCode;

        @JsonProperty("mobile")
        private String mobile;

        @JsonProperty("workCountryCode")
        private String workCountryCode;

        @JsonProperty("work")
        private String work;

        public String getHomeCountryCode() {
            return homeCountryCode;
        }

        public void setHomeCountryCode(String homeCountryCode) {
            this.homeCountryCode = homeCountryCode;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getMobileCountryCode() {
            return mobileCountryCode;
        }

        public void setMobileCountryCode(String mobileCountryCode) {
            this.mobileCountryCode = mobileCountryCode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getWorkCountryCode() {
            return workCountryCode;
        }

        public void setWorkCountryCode(String workCountryCode) {
            this.workCountryCode = workCountryCode;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }
    }

    public static class Associated {

        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Associated getAssociated() {
        return associated;
    }

    public void setAssociated(Associated associated) {
        this.associated = associated;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }
}
