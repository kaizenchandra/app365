package com.synechisveltiosi.apis.app365.address.entity;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "municipalities")
public class Municipality extends BaseEntity {

    @Column(name = "municipality_id", nullable = false, unique = true)
    private String municipalityId;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State stateId;

    @Column(name = "user_defined")
    private Boolean userDefined;

    public String getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(String municipalityId) {
        this.municipalityId = municipalityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getStateId() {
        return stateId;
    }

    public void setStateId(State stateId) {
        this.stateId = stateId;
    }

    public Boolean isUserDefined() {
        return userDefined != null && userDefined;
    }

    public void setUserDefined(Boolean userDefined) {
        this.userDefined = userDefined;
    }

    @PrePersist
    public void prePersist() {
        if (municipalityId == null) municipalityId = UUID.randomUUID().toString();
    }
}
