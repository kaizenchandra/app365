package com.synechisveltiosi.apis.app365.accounts.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ApiTokenConfig implements Serializable {

    private static final long serialVersionUID = 0L;

    private String service;
    private String appId;
    private String appSecret;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @JsonIgnore
    public boolean isValid() {
        return !StringUtils.isBlank(service) && !StringUtils.isBlank(appId) && !StringUtils.isBlank(appSecret);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ApiTokenConfig that = (ApiTokenConfig) o;

        return new EqualsBuilder()
                .append(service, that.service)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(service)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("service", service)
                .append("appId", appId)
                .append("appSecret", appSecret)
                .toString();
    }
}
