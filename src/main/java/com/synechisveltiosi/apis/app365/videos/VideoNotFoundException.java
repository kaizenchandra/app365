package com.synechisveltiosi.apis.app365.videos;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.NotFoundException;

public class VideoNotFoundException extends NotFoundException {

    public VideoNotFoundException() {
        this("Video not found.");
    }

    public VideoNotFoundException(String message) {
        super(message);
    }
}
