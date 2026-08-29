package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import org.springframework.http.HttpStatus;

public class TooManyRequestException extends RestResponseException {

    public TooManyRequestException() {
        this("Too many requests");
    }

    public TooManyRequestException(String message) {
        super(HttpStatus.TOO_MANY_REQUESTS, message);
    }
}
