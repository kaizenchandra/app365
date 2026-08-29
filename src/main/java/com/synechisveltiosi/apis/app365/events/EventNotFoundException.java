package com.synechisveltiosi.apis.app365.events;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class EventNotFoundException extends NotFoundException {

    public EventNotFoundException() {
        this("Event not found.");
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}
