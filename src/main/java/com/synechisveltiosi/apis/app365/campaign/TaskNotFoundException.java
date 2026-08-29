package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class TaskNotFoundException extends NotFoundException {

    public TaskNotFoundException() {
        this("Task not found.");
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
