package com.synechisveltiosi.apis.app365.users.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        this("User not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
