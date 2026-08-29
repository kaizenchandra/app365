package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends RestResponseException {

    public ConflictException() {
        this("Duplicate item");
    }

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
