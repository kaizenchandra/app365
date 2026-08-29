package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

import java.util.List;

public class ScheduleDuplicatedException extends ConflictException {

    public ScheduleDuplicatedException() {
        this("Schedule duplicated.");
    }

    public ScheduleDuplicatedException(String message) {
        super(message);
        this.setCode(ApiStatusCode.DUPLICATED_SCHEDULES);
    }

    public void setAdditionalErrors(List<? extends Object> errors) {
        if (errors == null) return;

        errors.forEach(this::addAdditionalError);
    }

    public void addAdditionalError(Object error) {
        this.error.addError(error);
    }
}
