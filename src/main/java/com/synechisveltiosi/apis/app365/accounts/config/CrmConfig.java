package com.synechisveltiosi.apis.app365.accounts.config;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class CrmConfig implements Serializable {

    private static final long serialVersionUID = 0L;

    private String tenantId;
    private String appId;
    private String appSecret;
    private String baseUrl;
    private Boolean idCardRequireDashes;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Boolean getIdCardRequireDashes() {
        return idCardRequireDashes != null && idCardRequireDashes;
    }

    public void setIdCardRequireDashes(Boolean idCardRequireDashes) {
        this.idCardRequireDashes = idCardRequireDashes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tenantId", tenantId)
                .append("appId", appId)
                .append("appSecret", appSecret)
                .append("baseUrl", baseUrl)
                .append("idCardRequireDashes", idCardRequireDashes)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CrmConfig crmConfig = (CrmConfig) o;

        return new EqualsBuilder()
                .append(tenantId, crmConfig.tenantId)
                .append(appId, crmConfig.appId)
                .append(appSecret, crmConfig.appSecret)
                .append(baseUrl, crmConfig.baseUrl)
                .append(idCardRequireDashes, crmConfig.idCardRequireDashes)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(tenantId)
                .append(appId)
                .append(appSecret)
                .append(baseUrl)
                .append(idCardRequireDashes)
                .toHashCode();
    }
}
