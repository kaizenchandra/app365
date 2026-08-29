package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class VolunteerNotFoundException extends NotFoundException {

    public VolunteerNotFoundException() {
        this("Volunteer not found.");
    }

    public VolunteerNotFoundException(String message) {
        super(message);
    }
}
