package com.synechisveltiosi.apis.app365.candidates;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException() {
        this("Post not found.");
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
