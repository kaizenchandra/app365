package com.synechisveltiosi.apis.app365.calendar;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class CalendarEntryNotFoundException extends NotFoundException {

    public CalendarEntryNotFoundException() {
        this("No calendar entry found for this event.");
    }

    public CalendarEntryNotFoundException(String message) {
        super(message);
    }
}
