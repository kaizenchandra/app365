package com.synechisveltiosi.apis.app365.campaign.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ForbiddenException;

import java.util.List;

public class SelfCoordinatorRegistrationException extends ForbiddenException {

    public SelfCoordinatorRegistrationException() {
        this("Self coordinator registration error.");
    }

    public SelfCoordinatorRegistrationException(String message) {
        super(message);
        this.setCode(ApiStatusCode.FORBIDDEN_SELF_COORDINATOR_REGISTRATION);
    }

    public void setAdditionalErrors(List<? extends Object> errors) {
        if (errors == null) return;

        errors.forEach(this::addAdditionalError);
    }

    public void addAdditionalError(Object error) {
        this.error.addError(error);
    }
}
