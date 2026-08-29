package com.synechisveltiosi.apis.app365.devices;

import com.synechisveltiosi.apis.app365.common.entity.base.BaseEntity;
import com.synechisveltiosi.apis.app365.users.entity.User;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "devices")
public class Device extends BaseEntity {

    @Column(name = "device_id")
    private String deviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "platform")
    private String platform;

    @Column(name = "firebase_token")
    private String firebaseToken;

    @Column(name = "created_at")
    private Date createdAt;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void prePersist() {
        if (deviceId == null) deviceId = UUID.randomUUID().toString();
        if (createdAt == null) createdAt = new Date();
    }
}
