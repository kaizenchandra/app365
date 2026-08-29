package com.synechisveltiosi.apis.app365.campaign.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

import java.util.List;

public class PhoneInvalidException extends ConflictException {

    public PhoneInvalidException() {
        this("Phone invalid.");
    }

    public PhoneInvalidException(String message) {
        super(message);
        this.setCode(ApiStatusCode.PHONE_NUMBER_INVALID);
    }

    public void setAdditionalErrors(List<? extends Object> errors) {
        if (errors == null) return;

        errors.forEach(this::addAdditionalError);
    }

    public void addAdditionalError(Object error) {
        this.error.addError(error);
    }
}
