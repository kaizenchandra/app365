package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class MilitantNotFoundException extends NotFoundException {

    public MilitantNotFoundException() {
        this("Militant not found.");
        this.setCode(ApiStatusCode.MILITANT_NOT_FOUND);
    }

    public MilitantNotFoundException(String message) {
        super(message);
    }
}
