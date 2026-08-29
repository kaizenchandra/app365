package com.synechisveltiosi.apis.app365.news;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class NewsNotFoundException extends NotFoundException {

    public NewsNotFoundException() {
        this("News not found.");
    }

    public NewsNotFoundException(String message) {
        super(message);
    }
}
