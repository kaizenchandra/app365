package com.synechisveltiosi.apis.app365.users.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

public class DuplicateUserException extends ConflictException {

    public DuplicateUserException() {
        this("Duplicate user, This ID Card, Email address or other unique information is already registered " +
                "with someone else.");
    }

    public DuplicateUserException(String message) {
        super(message);
    }
}
