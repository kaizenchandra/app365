package com.synechisveltiosi.apis.app365.address.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "states")
public class State extends BaseEntity {

    @Column(name = "state_id", nullable = false, unique = true)
    private String stateId;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country countryId;

    @Column(name = "user_defined")
    private Boolean userDefined;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String provinceId) {
        this.stateId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    public Boolean isUserDefined() {
        return userDefined != null && userDefined;
    }

    public void setUserDefined(Boolean userDefined) {
        this.userDefined = userDefined;
    }

    @PrePersist
    public void prePersist() {
        if (stateId == null) stateId = UUID.randomUUID().toString();
    }
}
