package com.synechisveltiosi.apis.app365.campaign;

import com.synechisveltiosi.apis.app365.common.rest.response.ApiStatusCode;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class TeamMemberNotFoundException extends NotFoundException {

    public TeamMemberNotFoundException() {
        this("Team member not found.");
        this.setCode(ApiStatusCode.MEMBER_NOT_FOUND);
    }

    public TeamMemberNotFoundException(String message) {
        super(message);
    }
}
