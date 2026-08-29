package com.synechisveltiosi.apis.app365.common.dto.places;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlatAddressResponse {

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private String state;

    @JsonProperty("municipality")
    private String municipality;

    @JsonProperty("city")
    private String city;

    @JsonProperty("sector")
    private String sector;

    @JsonProperty("line1")
    private String line1;

    @JsonProperty("line2")
    private String line2;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty(value = "fullAddress", access = JsonProperty.Access.READ_ONLY)
    private String fullAddress;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public FlatAddressResponse withCountry(String country) {
        this.country = country;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public FlatAddressResponse withProvince(String state) {
        this.state = state;
        return this;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String province) {
        this.municipality = municipality;
    }

    public FlatAddressResponse withMunicipality(String municipality) {
        this.municipality = municipality;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public FlatAddressResponse withCity(String city) {
        this.city = city;
        return this;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public FlatAddressResponse withSector(String sector) {
        this.sector = sector;
        return this;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public FlatAddressResponse withLine1(String line1) {
        this.line1 = line1;
        return this;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public FlatAddressResponse withLine2(String line2) {
        this.line2 = line2;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public FlatAddressResponse withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public FlatAddressResponse withReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String buildFullAddress() {
        fullAddress = StringUtils.join(
                new String[]{line1,
                        StringUtils.isNotEmpty(sector) ? sector : "",
                        StringUtils.isNotEmpty(city) ? city : "",
                        StringUtils.isNotEmpty(municipality) ? municipality : "",
                        StringUtils.isNotEmpty(state) ? state : "",
                        StringUtils.isNotEmpty(country) ? country : ""},
                ",");
        return fullAddress;
    }

    public FlatAddressResponse withFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("country", country)
                .append("state", state)
                .append("municipality", municipality)
                .append("city", city)
                .append("sector", sector)
                .append("line1", line1)
                .append("line2", line2)
                .append("zipCode", zipCode)
                .append("reference", reference)
                .append("fullAddress", fullAddress)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(country)
                .append(state)
                .append(municipality)
                .append(city)
                .append(sector)
                .append(line1)
                .append(line2)
                .append(zipCode)
                .append(reference)
                .append(fullAddress)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FlatAddressResponse)) {
            return false;
        }

        FlatAddressResponse rhs = ((FlatAddressResponse) other);
        return new EqualsBuilder()
                .append(country, rhs.country)
                .append(state, rhs.state)
                .append(municipality, rhs.municipality)
                .append(city, rhs.city)
                .append(sector, rhs.sector)
                .append(line1, rhs.line1)
                .append(line2, rhs.line2)
                .append(zipCode, rhs.zipCode)
                .append(reference, rhs.reference)
                .append(fullAddress, rhs.fullAddress)
                .isEquals();
    }
}
