package com.synechisveltiosi.apis.app365.address.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "sectors")
public class Sector extends BaseEntity {

    @Column(name = "sector_id", nullable = false, unique = true)
    private String sectorId;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City cityId;

    @Column(name = "user_defined")
    private Boolean userDefined;

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public Boolean isUserDefined() {
        return userDefined != null && userDefined;
    }

    public void setUserDefined(Boolean userDefined) {
        this.userDefined = userDefined;
    }

    @PrePersist
    public void prePersist() {
        if (sectorId == null) sectorId = UUID.randomUUID().toString();
    }
}
