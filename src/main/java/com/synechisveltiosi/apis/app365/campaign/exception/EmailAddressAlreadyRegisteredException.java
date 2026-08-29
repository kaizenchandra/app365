package com.synechisveltiosi.apis.app365.campaign.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

import java.util.List;

public class EmailAddressAlreadyRegisteredException extends ConflictException {

    public EmailAddressAlreadyRegisteredException() {
        this("Email Address already registered.");
    }

    public EmailAddressAlreadyRegisteredException(String message) {
        super(message);
        this.setCode(ApiStatusCode.DUPLICATED_EMAIL_ADDRESS);
    }

    public void setAdditionalErrors(List<? extends Object> errors) {
        if (errors == null) return;

        errors.forEach(this::addAdditionalError);
    }

    public void addAdditionalError(Object error) {
        this.error.addError(error);
    }
}
