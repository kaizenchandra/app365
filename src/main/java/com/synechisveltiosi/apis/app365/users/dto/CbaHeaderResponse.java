package com.synechisveltiosi.apis.app365.users.dto;

import com.synechisveltiosi.apis.app365.campaign.SupportSource;
import com.synechisveltiosi.apis.app365.campaign.dto.MilitantRequest;
import com.applepolitical.apis.applepolitical365.common.dto.places.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.dto.places.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CbaHeaderResponse {

    @JsonProperty("country")
    private CountryResponse country;

    @JsonProperty("state")
    private StateResponse state;

    @JsonProperty("district")
    private DistrictResponse district;

    @JsonProperty("municipality")
    private MunicipalityResponse municipality;

    @JsonProperty("city")
    private CityResponse city;

    @JsonProperty("supportSource")
    private SupportSource supportSource;

    @JsonProperty("associated")
    private MilitantRequest.Associated associatedOrganism;

    @JsonProperty("zone")
    private ZoneResponse zone;

    @JsonProperty("region")
    private RegionResponse region;

    public CountryResponse getCountry() {
        return country;
    }

    public void setCountry(CountryResponse country) {
        this.country = country;
    }

    public CbaHeaderResponse withCountry(CountryResponse country) {
        this.country = country;
        return this;
    }

    public StateResponse getState() {
        return state;
    }

    public void setState(StateResponse state) {
        this.state = state;
    }

    public CbaHeaderResponse withState(StateResponse state) {
        this.state = state;
        return this;
    }

    public DistrictResponse getDistrict() {
        return district;
    }

    public void setDistrict(DistrictResponse district) {
        this.district = district;
    }

    public CbaHeaderResponse withDistrict(DistrictResponse district) {
        this.district = district;
        return this;
    }

    public MunicipalityResponse getMunicipality() {
        return municipality;
    }

    public void setMunicipality(MunicipalityResponse municipality) {
        this.municipality = municipality;
    }

    public CbaHeaderResponse withMunicipality(MunicipalityResponse municipality) {
        this.municipality = municipality;
        return this;
    }

    public CityResponse getCity() {
        return city;
    }

    public void setCity(CityResponse city) {
        this.city = city;
    }

    public CbaHeaderResponse withCity(CityResponse city) {
        this.city = city;
        return this;
    }

    public SupportSource getSupportSource() {
        return supportSource;
    }

    public void setSupportSource(SupportSource supportSource) {
        this.supportSource = supportSource;
    }

    public CbaHeaderResponse withSupportSource(SupportSource supportSource) {
        this.supportSource = supportSource;
        return this;
    }

    public MilitantRequest.Associated getAssociatedOrganism() {
        return associatedOrganism;
    }

    public void setAssociatedOrganism(MilitantRequest.Associated associatedOrganism) {
        this.associatedOrganism = associatedOrganism;
    }

    public CbaHeaderResponse withAssociatedOrganism(MilitantRequest.Associated associatedOrganism) {
        this.associatedOrganism = associatedOrganism;
        return this;
    }

    public ZoneResponse getZone() {
        return zone;
    }

    public void setZone(ZoneResponse zone) {
        this.zone = zone;
    }

    public CbaHeaderResponse withZone(ZoneResponse zone) {
        this.zone = zone;
        return this;
    }

    public RegionResponse getRegion() {
        return region;
    }

    public void setRegion(RegionResponse region) {
        this.region = region;
    }

    public CbaHeaderResponse withRegion(RegionResponse region) {
        this.region = region;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("country", country)
                .append("state", state)
                .append("district", district)
                .append("municipality", municipality)
                .append("city", city)
                .append("supportSource", supportSource)
                .append("associatedOrganism", associatedOrganism)
                .append("zone", zone)
                .append("region", region)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CbaHeaderResponse that = (CbaHeaderResponse) o;

        return new EqualsBuilder()
                .append(getCountry(), that.getCountry())
                .append(getState(), that.getState())
                .append(getDistrict(), that.getDistrict())
                .append(getMunicipality(), that.getMunicipality())
                .append(getCity(), that.getCity())
                .append(getSupportSource(), that.getSupportSource())
                .append(getAssociatedOrganism(), that.getAssociatedOrganism())
                .append(getZone(), that.getZone())
                .append(getRegion(), that.getRegion())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getCountry())
                .append(getState())
                .append(getDistrict())
                .append(getMunicipality())
                .append(getCity())
                .append(getSupportSource())
                .append(getAssociatedOrganism())
                .append(getZone())
                .append(getRegion())
                .toHashCode();
    }
}
