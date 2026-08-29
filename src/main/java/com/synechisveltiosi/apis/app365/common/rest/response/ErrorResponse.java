
package com.synechisveltiosi.apis.app365.common.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("type")
    private String type;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private Date timestamp;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("errors")
    private List<? super Object> errors;

    public ErrorResponse() {

    }

    public ErrorResponse(Integer status) {
        this.status = status;
    }

    public ErrorResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ErrorResponse withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ErrorResponse withCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ErrorResponse withType(String type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public ErrorResponse withTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public ErrorResponse withRequestId(String requestId) {
        setRequestId(requestId);
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ErrorResponse withData(Object data) {
        this.data = data;
        return this;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public void addError(Object error) {
        if (error == null) return;

        if (this.errors == null)
            this.errors = new ArrayList<>();

        this.errors.add(error);
    }

    public ErrorResponse withErrors(List<Object> errors) {
        this.errors = errors;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("code", code)
                .append("type", type)
                .append("message", message)
                .append("timestamp", timestamp)
                .append("requestId", requestId)
                .append("data", data)
                .append("errors", errors)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(code)
                .append(type)
                .append(message)
                .append(status)
                .append(timestamp)
                .append(requestId)
                .append(data)
                .append(errors)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ErrorResponse)) {
            return false;
        }

        ErrorResponse rhs = ((ErrorResponse) other);
        return new EqualsBuilder()
                .append(code, rhs.code)
                .append(type, rhs.type)
                .append(message, rhs.message)
                .append(status, rhs.status)
                .append(timestamp, rhs.timestamp)
                .append(requestId, rhs.requestId)
                .append(data, rhs.data)
                .append(errors, rhs.errors)
                .isEquals();
    }
}
