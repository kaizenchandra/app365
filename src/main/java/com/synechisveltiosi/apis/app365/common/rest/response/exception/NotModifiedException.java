package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import org.springframework.http.HttpStatus;

public class NotModifiedException extends RestResponseException {

    public NotModifiedException() {
        this("Not modified");
    }

    public NotModifiedException(String message) {
        super(HttpStatus.NOT_MODIFIED, message);
    }
}
