package com.synechisveltiosi.apis.app365.campaign.dto;

import com.synechisveltiosi.apis.app365.common.dto.id.IdRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Alfredo Martinez <martin3zra@gmail.com> on 11/25/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CbaHeaderRequest {

    @JsonProperty("country")
    private IdRequest country;

    @JsonProperty("state")
    private IdRequest state;

    @JsonProperty("municipality")
    private IdRequest municipality;

    @JsonProperty("city")
    private IdRequest city;

    @JsonProperty("supportSource")
    private IdRequest supportSource;

    @JsonProperty("associated")
    private IdRequest associated;

    @JsonProperty("zone")
    private IdRequest zone;

    @JsonProperty("region")
    private IdRequest region;

    @JsonProperty("district")
    private IdRequest district;

    public IdRequest getCountry() {
        return country;
    }

    public void setCountry(IdRequest country) {
        this.country = country;
    }

    public IdRequest getState() {
        return state;
    }

    public void setState(IdRequest state) {
        this.state = state;
    }

    public IdRequest getMunicipality() {
        return municipality;
    }

    public void setMunicipality(IdRequest municipality) {
        this.municipality = municipality;
    }

    public IdRequest getCity() {
        return city;
    }

    public void setCity(IdRequest city) {
        this.city = city;
    }

    public IdRequest getSupportSource() {
        return supportSource;
    }

    public void setSupportSource(IdRequest supportSource) {
        this.supportSource = supportSource;
    }

    public IdRequest getAssociated() {
        return associated;
    }

    public void setAssociated(IdRequest associated) {
        this.associated = associated;
    }

    public IdRequest getZone() {
        return zone;
    }

    public void setZone(IdRequest zone) {
        this.zone = zone;
    }

    public IdRequest getRegion() {
        return region;
    }

    public void setRegion(IdRequest region) {
        this.region = region;
    }

    public IdRequest getDistrict() {
        return district;
    }

    public void setDistrict(IdRequest district) {
        this.district = district;
    }
}
