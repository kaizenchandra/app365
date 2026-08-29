package com.synechisveltiosi.apis.app365.address.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class MunicipalityNotFoundException extends NotFoundException {

    public MunicipalityNotFoundException() {
        this("Municipality not found.");
    }

    public MunicipalityNotFoundException(String message) {
        super(message);
    }
}
