package com.synechisveltiosi.apis.app365.campaign.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

import java.util.List;

public class EmailAddressInvalidException extends ConflictException {

    public EmailAddressInvalidException() {
        this("Email Address invalid.");
    }

    public EmailAddressInvalidException(String message) {
        super(message);
        this.setCode(ApiStatusCode.EMAIL_ADDRESS_INVALID);
    }

    public void setAdditionalErrors(List<? extends Object> errors) {
        if (errors == null) return;

        errors.forEach(this::addAdditionalError);
    }

    public void addAdditionalError(Object error) {
        this.error.addError(error);
    }
}
