package com.synechisveltiosi.apis.app365.actions.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class MedalNotFoundException extends NotFoundException {

    public MedalNotFoundException() {
        this("Medal not found");
    }

    public MedalNotFoundException(String message) {
        super(message);
    }
}
