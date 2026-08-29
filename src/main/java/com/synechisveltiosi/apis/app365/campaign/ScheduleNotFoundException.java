package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class ScheduleNotFoundException extends NotFoundException {

    public ScheduleNotFoundException() {
        this("Schedule not found.");
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
