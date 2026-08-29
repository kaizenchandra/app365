package com.synechisveltiosi.apis.app365.campaign.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

import java.util.List;

public class PhoneDuplicatedException extends ConflictException {

    public PhoneDuplicatedException() {
        this("Phone duplicated.");
    }

    public PhoneDuplicatedException(String message) {
        super(message);
        this.setCode(ApiStatusCode.DUPLICATED_PHONE_NUMBER);
    }

    public void setAdditionalErrors(List<? extends Object> errors) {
        if (errors == null) return;

        errors.forEach(this::addAdditionalError);
    }

    public void addAdditionalError(Object error) {
        this.error.addError(error);
    }
}
