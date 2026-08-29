package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientResponseException;

import java.nio.charset.Charset;

public class RestResponseException extends RestClientResponseException {

    private static final long serialVersionUID = 5696801857651587810L;
    private final HttpStatus statusCode;
    protected ErrorResponse error = new ErrorResponse();

    public RestResponseException(HttpStatus statusCode) {
        this(statusCode, statusCode.name(), null, null, null);
    }

    public RestResponseException(HttpStatus statusCode, String statusText) {
        this(statusCode, statusText, null, null, null);
    }

    public RestResponseException(HttpStatus statusCode, String statusText, byte[] responseBody, Charset responseCharset) {
        this(statusCode, statusText, null, responseBody, responseCharset);
    }

    public RestResponseException(HttpStatus statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusText, statusCode.value(), statusText, responseHeaders, responseBody, responseCharset);
        this.statusCode = statusCode;

        this.initError();
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

    private void initError() {
        error.setStatus(this.getStatusCode().value());
        error.setMessage(this.getMessage());
    }

    public ErrorResponse getError() {
        return error;
    }

    public Integer getCode() {
        return error.getCode();
    }

    protected void setCode(Integer code) {
        error.setCode(code);
    }

    public String getType() {
        return error.getType();
    }

    protected void setType(String type) {
        error.setType(type);
    }
}
