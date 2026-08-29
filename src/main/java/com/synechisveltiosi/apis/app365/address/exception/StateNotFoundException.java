package com.synechisveltiosi.apis.app365.address.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class StateNotFoundException extends NotFoundException {

    public StateNotFoundException() {
        this("State not found.");
    }

    public StateNotFoundException(String message) {
        super(message);
    }
}
