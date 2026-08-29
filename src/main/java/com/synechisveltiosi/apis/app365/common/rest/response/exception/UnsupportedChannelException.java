package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import org.springframework.http.HttpStatus;

public class UnsupportedChannelException extends RestResponseException {

    public UnsupportedChannelException() {
        this("Duplicate item exception.");
    }

    public UnsupportedChannelException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
        this.setCode(ApiStatusCode.UNSUPPORTED_CHANNEL);
    }
}
