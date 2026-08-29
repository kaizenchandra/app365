package com.synechisveltiosi.apis.app365.common.rest.response;

public class ValidationError {

    private String field;
    private String message;

    public ValidationError() {

    }

    public ValidationError(String field, String message) {
        this.setField(field);
        this.setMessage(message);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ValidationError withField(String field) {
        setField(field);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidationError withMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return getField() + ": " + getMessage();
    }
}
