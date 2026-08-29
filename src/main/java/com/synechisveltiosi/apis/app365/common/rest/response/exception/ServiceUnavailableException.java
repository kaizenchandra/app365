package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends RestResponseException {

    public ServiceUnavailableException() {
        this("Service unavailable");
    }

    public ServiceUnavailableException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
    }
}
