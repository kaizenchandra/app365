package com.synechisveltiosi.apis.app365.accounts.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class AccountNotFoundException extends NotFoundException {

    public AccountNotFoundException() {
        this("Account not found");
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
