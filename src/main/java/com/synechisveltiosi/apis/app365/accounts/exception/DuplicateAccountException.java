package com.synechisveltiosi.apis.app365.accounts.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

public class DuplicateAccountException extends ConflictException {

    public DuplicateAccountException() {
        this("Duplicate account.");
    }

    public DuplicateAccountException(String message) {
        super(message);
    }
}
