
package com.synechisveltiosi.apis.app365.common.dto.places;

import com.synechisveltiosi.apis.app365.common.dto.id.IdRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressRequest {

    @JsonProperty("country")
    private IdRequest country;

    @JsonProperty("province")
    private IdRequest province;

    @JsonProperty("municipality")
    private IdRequest municipality;

    @JsonProperty("city")
    private IdRequest city;

    @JsonProperty("sector")
    private IdRequest sector;

    @JsonProperty("line1")
    private String line1;

    @JsonProperty("line2")
    private String line2;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("reference")
    private String reference;

    public IdRequest getCountry() {
        return country;
    }

    public void setCountry(IdRequest country) {
        this.country = country;
    }

    public AddressRequest withCountry(IdRequest country) {
        this.country = country;
        return this;
    }

    public IdRequest getProvince() {
        return province;
    }

    public void setProvince(IdRequest province) {
        this.province = province;
    }

    public AddressRequest withProvince(IdRequest province) {
        this.province = province;
        return this;
    }

    public IdRequest getMunicipality() {
        return municipality;
    }

    public void setMunicipality(IdRequest municipality) {
        this.municipality = municipality;
    }

    public AddressRequest withMunicipality(IdRequest municipality) {
        this.municipality = municipality;
        return this;
    }

    public IdRequest getCity() {
        return city;
    }

    public void setCity(IdRequest city) {
        this.city = city;
    }

    public AddressRequest withCity(IdRequest city) {
        this.city = city;
        return this;
    }

    public IdRequest getSector() {
        return sector;
    }

    public void setSector(IdRequest sector) {
        this.sector = sector;
    }

    public AddressRequest withSector(IdRequest sector) {
        this.sector = sector;
        return this;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public AddressRequest withLine1(String line1) {
        this.line1 = line1;
        return this;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public AddressRequest withLine2(String line2) {
        this.line2 = line2;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public AddressRequest withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public AddressRequest withReference(String reference) {
        this.reference = reference;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("country", country)
                .append("province", province)
                .append("municipality", municipality)
                .append("city", city)
                .append("sector", sector)
                .append("line1", line1)
                .append("line2", line2)
                .append("zipCode", zipCode)
                .append("reference", reference)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(reference)
                .append(country)
                .append(province)
                .append(city)
                .append(municipality)
                .append(sector)
                .append(line1)
                .append(line2)
                .append(zipCode)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddressRequest)) {
            return false;
        }

        AddressRequest rhs = ((AddressRequest) other);
        return new EqualsBuilder()
                .append(reference, rhs.reference)
                .append(country, rhs.country)
                .append(province, rhs.province)
                .append(city, rhs.city)
                .append(municipality, rhs.municipality)
                .append(sector, rhs.sector)
                .append(line1, rhs.line1)
                .append(line2, rhs.line2)
                .append(zipCode, rhs.zipCode)
                .isEquals();
    }
}
