package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RestResponseException {

    public BadRequestException() {
        this("Bad request");
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
