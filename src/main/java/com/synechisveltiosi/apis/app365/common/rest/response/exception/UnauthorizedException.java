package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RestResponseException {

    public UnauthorizedException() {
        this("Unauthorized");
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
