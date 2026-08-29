package com.synechisveltiosi.apis.app365.devices;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class DeviceNotFoundException extends NotFoundException {

    public DeviceNotFoundException() {
        this("Device not found.");
    }

    public DeviceNotFoundException(String message) {
        super(message);
    }
}
