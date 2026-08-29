package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class TeamNotFoundException extends NotFoundException {

    public TeamNotFoundException() {
        this("Team not found.");
        this.setCode(ApiStatusCode.TEAM_NOT_FOUND);
    }

    public TeamNotFoundException(String message) {
        super(message);
    }
}
