package com.synechisveltiosi.apis.app365.users.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class AwardNotFoundException extends NotFoundException {

    public AwardNotFoundException() {
        this("Award not found");
    }

    public AwardNotFoundException(String message) {
        super(message);
    }
}
