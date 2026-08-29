package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RestResponseException {

    public NotFoundException() {
        this("Item not found");
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
