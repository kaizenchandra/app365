package com.synechisveltiosi.apis.app365.accounts.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.accounts.config.CrmConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountConfigurationResponse {

    @JsonProperty("crm")
    private CrmConfig crmConfig;

    public CrmConfig getCrmConfig() {
        return crmConfig;
    }

    public void setCrmConfig(CrmConfig crmConfig) {
        this.crmConfig = crmConfig;
    }
}
