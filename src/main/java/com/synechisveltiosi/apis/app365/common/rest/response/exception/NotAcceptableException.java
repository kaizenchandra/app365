package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import org.springframework.http.HttpStatus;

public class NotAcceptableException extends RestResponseException {

    public NotAcceptableException() {
        this("Not acceptable");
    }

    public NotAcceptableException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}
