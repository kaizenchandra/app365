package com.synechisveltiosi.apis.app365.notifications;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class NotificationNotFoundException extends NotFoundException {

    public NotificationNotFoundException() {
        this("Notification not found");
    }

    public NotificationNotFoundException(String message) {
        super(message);
    }
}
