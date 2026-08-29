package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class CoordinatorNotFoundException extends NotFoundException {

    public CoordinatorNotFoundException() {
        this("Coordinator not found.");
        this.setCode(ApiStatusCode.COORDINATOR_NOT_FOUND);
    }

    public CoordinatorNotFoundException(String message) {
        super(message);
    }
}
