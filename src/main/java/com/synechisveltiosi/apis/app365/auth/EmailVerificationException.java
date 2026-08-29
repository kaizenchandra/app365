package com.synechisveltiosi.apis.app365.auth;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;

public class EmailVerificationException extends BadRequestException {

    public EmailVerificationException() {
        this("Invalid email verification code.");
    }

    public EmailVerificationException(String message) {
        super(message);
        this.setCode(ApiStatusCode.EMAIL_VERIFICATION_FAILED);
    }
}
