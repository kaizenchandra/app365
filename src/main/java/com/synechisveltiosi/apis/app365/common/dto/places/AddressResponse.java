package com.synechisveltiosi.apis.app365.common.dto.places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponse {

    @JsonProperty("country")
    private CountryResponse country;

    @JsonProperty("state")
    private StateResponse state;

    @JsonProperty("municipality")
    private MunicipalityResponse municipality;

    @JsonProperty("city")
    private CityResponse city;

    @JsonProperty("sector")
    private SectorResponse sector;

    @JsonProperty("demarcation")
    private DemarcationResponse demarcation;

    @JsonProperty("street")
    private String street;

    @JsonProperty("home")
    private String home;

    @JsonProperty("apartment")
    private String apartment;

    @JsonProperty("line")
    private String line;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("reference")
    private String reference;

    @JsonProperty("additionalSector")
    private String additionalSector;

    @JsonProperty(value = "fullAddress", access = JsonProperty.Access.READ_ONLY)
    private String fullAddress;

    public CountryResponse getCountry() {
        return country;
    }

    public void setCountry(CountryResponse country) {
        this.country = country;
    }

    public AddressResponse withCountry(CountryResponse country) {
        this.country = country;
        return this;
    }

    public StateResponse getState() {
        return state;
    }

    public void setState(StateResponse state) {
        this.state = state;
    }

    public AddressResponse withProvince(StateResponse province) {
        this.state = province;
        return this;
    }

    public MunicipalityResponse getMunicipality() {
        return municipality;
    }

    public void setMunicipality(MunicipalityResponse municipality) {
        this.municipality = municipality;
    }

    public AddressResponse withMunicipality(MunicipalityResponse municipality) {
        this.municipality = municipality;
        return this;
    }

    public CityResponse getCity() {
        return city;
    }

    public void setCity(CityResponse city) {
        this.city = city;
    }

    public AddressResponse withCity(CityResponse city) {
        this.city = city;
        return this;
    }

    public SectorResponse getSector() {
        return sector;
    }

    public void setSector(SectorResponse sector) {
        this.sector = sector;
    }

    public AddressResponse withSector(SectorResponse sector) {
        this.sector = sector;
        return this;
    }

    public DemarcationResponse getDemarcation() {
        return demarcation;
    }

    public void setDemarcation(DemarcationResponse demarcation) {
        this.demarcation = demarcation;
    }

    public AddressResponse withDemarcation(DemarcationResponse demarcation) {
        this.demarcation = demarcation;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public AddressResponse withStreet(String street) {
        this.street = street;
        return this;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public AddressResponse withHome(String home) {
        this.home = home;
        return this;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public AddressResponse withApartment1(String apartment) {
        this.apartment = apartment;
        return this;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public AddressResponse withLine(String line) {
        this.line = line;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public AddressResponse withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public AddressResponse withReference(String reference) {
        this.reference = reference;
        return this;
    }

    public String getAdditionalSector() {
        return additionalSector;
    }

    public void setAdditionalSector(String additionalSector) {
        this.additionalSector = additionalSector;
    }

    public AddressResponse withAdditionalSector(String additionalSector) {
        this.additionalSector = additionalSector;
        return this;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("country", country)
                .append("state", state)
                .append("municipality", municipality)
                .append("city", city)
                .append("sector", sector)
                .append("line", line)
                .append("reference", reference)
                .append("additionalSector", additionalSector)
                .append("fullAddress", fullAddress)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(additionalSector)
                .append(reference)
                .append(country)
                .append(state)
                .append(city)
                .append(municipality)
                .append(fullAddress)
                .append(sector)
                .append(line)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddressResponse)) {
            return false;
        }

        AddressResponse rhs = ((AddressResponse) other);
        return new EqualsBuilder()
                .append(additionalSector, rhs.additionalSector)
                .append(reference, rhs.reference)
                .append(country, rhs.country)
                .append(state, rhs.state)
                .append(city, rhs.city)
                .append(municipality, rhs.municipality)
                .append(fullAddress, rhs.fullAddress)
                .append(sector, rhs.sector)
                .append(line, rhs.line)
                .isEquals();
    }
}
