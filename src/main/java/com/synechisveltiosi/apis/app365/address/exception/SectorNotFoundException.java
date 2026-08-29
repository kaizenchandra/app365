package com.synechisveltiosi.apis.app365.address.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class SectorNotFoundException extends NotFoundException {

    public SectorNotFoundException() {
        this("Sector not found.");
    }

    public SectorNotFoundException(String message) {
        super(message);
    }
}
