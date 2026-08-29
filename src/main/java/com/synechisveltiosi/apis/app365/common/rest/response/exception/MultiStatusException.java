package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public class MultiStatusException extends RestResponseException {

    public MultiStatusException() {
        this("Multi status");
    }

    public MultiStatusException(String message) {
        this(message, null);
    }

    public MultiStatusException(String message, ErrorResponse additionalError) {
        this(message, null, additionalError);
    }

    public MultiStatusException(String message, Object data) {
        this(message, data, null);
    }

    public MultiStatusException(String message, Object data, ErrorResponse additionalError) {
        super(HttpStatus.MULTI_STATUS, message);
        this.setData(data);
        this.addAdditionalError(additionalError);
    }

    public void setData(Object data) {
        this.error.setData(data);
    }

    public void setAdditionalErrors(List<? super Object> errors) {
        error.setErrors(errors);
    }

    public void addAdditionalError(ErrorResponse error) {
        this.error.addError(error);
    }
}
