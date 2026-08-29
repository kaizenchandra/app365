package com.synechisveltiosi.apis.app365.common.sms;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;

public class PhoneVerificationException extends BadRequestException {

    public PhoneVerificationException() {
        this("Phone verification exception.");
    }

    public PhoneVerificationException(String message) {
        super(message);
        this.setCode(ApiStatusCode.PHONE_VERIFICATION_FAILED);
    }
}
