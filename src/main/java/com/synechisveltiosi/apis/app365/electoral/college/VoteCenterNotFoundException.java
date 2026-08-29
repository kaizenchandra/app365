package com.synechisveltiosi.apis.app365.electoral.college;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class VoteCenterNotFoundException extends NotFoundException {

    public VoteCenterNotFoundException() {
        this("Vote center not found");
    }

    public VoteCenterNotFoundException(String message) {
        super(message);
    }
}
