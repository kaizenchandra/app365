package com.synechisveltiosi.apis.app365.election;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class ElectionNotFoundException extends NotFoundException {

    public ElectionNotFoundException() {
        this("Election not found");
    }

    public ElectionNotFoundException(String message) {
        super(message);
    }
}
