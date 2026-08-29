package com.synechisveltiosi.apis.app365.actions.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class MedalRuleNotFoundException extends NotFoundException {

    public MedalRuleNotFoundException() {
        this("Medal rule not found");
    }

    public MedalRuleNotFoundException(String message) {
        super(message);
    }
}
