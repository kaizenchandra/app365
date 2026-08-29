package com.synechisveltiosi.apis.app365.actions.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class ActionTypeNameNotFoundException extends NotFoundException {

    public ActionTypeNameNotFoundException() {
        this("Action type name not found");
    }

    public ActionTypeNameNotFoundException(String message) {
        super(message);
    }
}
