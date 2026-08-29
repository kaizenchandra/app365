package com.synechisveltiosi.apis.app365.citizens.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class CitizenNotFoundException extends NotFoundException {

    public CitizenNotFoundException() {
        this("Citizen not found.");
    }

    public CitizenNotFoundException(String message) {
        super(message);
    }
}
