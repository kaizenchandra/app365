package com.synechisveltiosi.apis.app365.address.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "cities")
public class City extends BaseEntity {

    @Column(name = "city_id", nullable = false, unique = true)
    private String cityId;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    private Municipality municipalityId;

    @Column(name = "user_defined")
    private Boolean userDefined;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Municipality getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(Municipality municipalityId) {
        this.municipalityId = municipalityId;
    }

    public Boolean isUserDefined() {
        return userDefined != null && userDefined;
    }

    public void setUserDefined(Boolean userDefined) {
        this.userDefined = userDefined;
    }

    @PrePersist
    public void prePersist() {
        if (cityId == null) cityId = UUID.randomUUID().toString();
    }
}
