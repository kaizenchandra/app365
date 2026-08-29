package com.synechisveltiosi.apis.app365.actions.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class PointRuleNotFoundException extends NotFoundException {

    public PointRuleNotFoundException() {
        this("Point rule not found");
    }

    public PointRuleNotFoundException(String message) {
        super(message);
    }
}
