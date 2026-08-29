
package com.synechisveltiosi.apis.app365.devices;

import com.synechisveltiosi.apis.app365.common.util.Platform;
import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("platform")
    private Platform platform;

    @JsonProperty("firebaseToken")
    private String firebaseToken;

    @JsonProperty("createdAt")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DeviceResponse withId(String id) {
        this.id = id;
        return this;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public DeviceResponse withPlatform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public DeviceResponse withFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public DeviceResponse withCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("platform", platform)
                .append("firebaseToken", firebaseToken)
                .append("createdAt", createdAt)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(createdAt)
                .append(id)
                .append(firebaseToken)
                .append(platform)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeviceResponse)) {
            return false;
        }

        DeviceResponse rhs = ((DeviceResponse) other);
        return new EqualsBuilder()
                .append(createdAt, rhs.createdAt)
                .append(id, rhs.id)
                .append(firebaseToken, rhs.firebaseToken)
                .append(platform, rhs.platform)
                .isEquals();
    }
}
