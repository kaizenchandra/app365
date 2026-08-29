package com.synechisveltiosi.apis.app365.candidates;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class CandidateNotFoundException extends NotFoundException {

    public CandidateNotFoundException() {
        this("Candidate not found.");
    }

    public CandidateNotFoundException(String message) {
        super(message);
    }
}
