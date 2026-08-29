package com.synechisveltiosi.apis.app365.campaign.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

import java.util.List;

public class CoordinatorDuplicatedException extends ConflictException {

    public CoordinatorDuplicatedException() {
        this("Coordinator duplicated.");
    }

    public CoordinatorDuplicatedException(String message) {
        super(message);
        this.setCode(ApiStatusCode.DUPLICATED_COORDINATOR);
    }

    public void setAdditionalErrors(List<? extends Object> errors) {
        if (errors == null) return;

        errors.forEach(this::addAdditionalError);
    }

    public void addAdditionalError(Object error) {
        this.error.addError(error);
    }
}
