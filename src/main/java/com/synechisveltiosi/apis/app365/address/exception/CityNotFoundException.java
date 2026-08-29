package com.synechisveltiosi.apis.app365.address.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class CityNotFoundException extends NotFoundException {

    public CityNotFoundException() {
        this("City not found.");
    }

    public CityNotFoundException(String message) {
        super(message);
    }
}
