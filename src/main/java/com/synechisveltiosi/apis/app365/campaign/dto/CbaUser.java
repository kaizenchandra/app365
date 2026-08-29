package com.synechisveltiosi.apis.app365.campaign.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.users.dto.CbaHeaderResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CbaUser {

    @JsonProperty("cbaHeader")
    private CbaHeaderResponse cbaHeader;

    public CbaHeaderResponse getCbaHeader() {
        return cbaHeader;
    }

    public void setCbaHeader(CbaHeaderResponse cbaHeader) {
        this.cbaHeader = cbaHeader;
    }

    public CbaUser withCbaHeader(CbaHeaderResponse cbaHeader) {
        this.cbaHeader = cbaHeader;
        return this;
    }
}
