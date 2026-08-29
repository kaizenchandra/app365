package com.synechisveltiosi.apis.app365.address.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column(name = "country_id", nullable = false, unique = true)
    private String countryId;

    @Column(name = "iso2", unique = true)
    private String iso2;

    @Column(name = "name")
    private String name;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PrePersist
    public void prePersist() {
        if (countryId == null) countryId = UUID.randomUUID().toString();
    }
}
