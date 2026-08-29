package com.synechisveltiosi.apis.app365.citizens.entity;

import com.synechisveltiosi.apis.app365.address.entity.*;
import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "citizen_addresses")
public class CitizenAddress extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", nullable = false)
    private Citizen citizenId;

    @Column(name = "street")
    private String street;

    @Column(name = "home")
    private String home;

    @Column(name = "apartment")
    private String apartment;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "reference")
    private String reference;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country countryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", nullable = false)
    private State stateId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    private Municipality municipalityId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City cityId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sector sectorId;

    public Citizen getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(Citizen userId) {
        this.citizenId = userId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    public Municipality getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Municipality municipalityId) {
        this.municipalityId = municipalityId;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public Sector getSectorId() {
        return sectorId;
    }

    public void setSectorId(Sector sectorId) {
        this.sectorId = sectorId;
    }
}
