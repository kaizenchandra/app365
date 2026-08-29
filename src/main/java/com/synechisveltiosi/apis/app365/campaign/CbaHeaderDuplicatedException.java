package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

import java.util.List;

public class CbaHeaderDuplicatedException extends ConflictException {

    public CbaHeaderDuplicatedException() {
        this("CBA Header duplicated.");
    }

    public CbaHeaderDuplicatedException(String message) {
        super(message);
        this.setCode(ApiStatusCode.DUPLICATED_CBA_HEADER);
    }

    public void setAdditionalErrors(List<? extends Object> errors) {
        if (errors == null) return;

        errors.forEach(this::addAdditionalError);
    }

    public void addAdditionalError(Object error) {
        this.error.addError(error);
    }
}
