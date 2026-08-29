package com.synechisveltiosi.apis.app365.address.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class CountryNotFoundException extends NotFoundException {

    public CountryNotFoundException() {
        this("Country not found.");
    }

    public CountryNotFoundException(String message) {
        super(message);
    }
}
