package com.synechisveltiosi.apis.app365.common.rest.response.exception;

import com.synechisveltiosi.apis.app365.common.rest.response.ErrorResponse;
import com.synechisveltiosi.apis.app365.common.rest.response.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private final Environment environment;

    @Autowired
    public RestResponseExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    private static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        ex.printStackTrace();

        String bodyOfResponse = "Unknown error message";

        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> illegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultiStatusException.class)
    public ResponseEntity<Object> multiStatus(MultiStatusException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler(NotModifiedException.class)
    public ResponseEntity<Object> notModified(NotModifiedException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> unauthorized(UnauthorizedException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler(TooManyRequestException.class)
    public ResponseEntity<Object> tooManyRequests(TooManyRequestException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> serviceUnavailable(ServiceUnavailableException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler(NotAcceptableException.class)
    public ResponseEntity<Object> notAcceptable(NotAcceptableException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequest(BadRequestException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFound(NotFoundException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> conflict(ConflictException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler(UnsupportedChannelException.class)
    public ResponseEntity<Object> unsupportedChannel(UnsupportedChannelException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getResponseHeaders(), ex.getStatusCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Build errors
        ErrorResponse error = new ErrorResponse(status.value(), "Invalid request arguments.");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            error.addError(new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
            error.addError(new ValidationError(objectError.getObjectName(), objectError.getDefaultMessage()));
        }

        return new ResponseEntity<>(error, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        ErrorResponse error = new ErrorResponse(status.value(),
                Optional.ofNullable(body).map(String::valueOf).orElseGet(ex::getMessage));

        return new ResponseEntity<>(error, headers, status);
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

                Map<String, Object> newErrorAttributes = new LinkedHashMap<>();
                newErrorAttributes.put("status", errorAttributes.getOrDefault("status", 0));
                newErrorAttributes.put("error", errorAttributes.getOrDefault("error", "Unknown error"));
                newErrorAttributes.put("message", errorAttributes.getOrDefault("message", null));

                // Override database exceptions message
                Throwable throwable = super.getError(webRequest);
                if (throwable instanceof DataAccessException || throwable instanceof SQLException) {
                    // Log error message
                    logger.error(throwable.getMessage(), throwable);

                    newErrorAttributes.put("error", "Database error, please report to system administrator.");
                    newErrorAttributes.put("message", null);
                }

                return newErrorAttributes;
            }
        };
    }
}
