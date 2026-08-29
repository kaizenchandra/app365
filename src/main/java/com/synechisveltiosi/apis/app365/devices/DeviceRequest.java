package com.synechisveltiosi.apis.app365.devices;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.util.Platform;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceRequest {

    @JsonProperty("deviceId")
    private String deviceId;

    @JsonProperty("platform")
    private Platform platform;

    @JsonProperty("firebaseToken")
    private String firebaseToken;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceRequest withDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public DeviceRequest withPlatform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public DeviceRequest withFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("deviceId", deviceId)
                .append("platform", platform)
                .append("firebaseToken", firebaseToken)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(deviceId)
                .append(firebaseToken)
                .append(platform)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeviceRequest)) {
            return false;
        }

        DeviceRequest rhs = ((DeviceRequest) other);
        return new EqualsBuilder()
                .append(deviceId, rhs.deviceId)
                .append(firebaseToken, rhs.firebaseToken)
                .append(platform, rhs.platform)
                .isEquals();
    }
}
