package com.synechisveltiosi.apis.app365.accounts.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;

public class DuplicateTenantException extends ConflictException {

    public DuplicateTenantException() {
        this("Duplicate tenant subdomain.");
    }

    public DuplicateTenantException(String message) {
        super(message);
    }
}
