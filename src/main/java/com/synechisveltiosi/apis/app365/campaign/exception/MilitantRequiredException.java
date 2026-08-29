package com.synechisveltiosi.apis.app365.campaign.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;

import java.util.List;

public class MilitantRequiredException extends BadRequestException {

    public MilitantRequiredException() {
        this("Militant required to proceed.");
    }

    public MilitantRequiredException(String message) {
        super(message);
        this.setCode(ApiStatusCode.BAD_REQUEST_MILITANT_REQUIRED);
    }

    public void setAdditionalErrors(List<? extends Object> errors) {
        if (errors == null) return;

        errors.forEach(this::addAdditionalError);
    }

    public void addAdditionalError(Object error) {
        this.error.addError(error);
    }
}
