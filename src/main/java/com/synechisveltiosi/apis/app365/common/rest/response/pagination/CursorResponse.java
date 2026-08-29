
package com.synechisveltiosi.apis.app365.common.rest.response.pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursorResponse {

    @JsonProperty("next")
    private String next;

    public CursorResponse() {

    }

    public CursorResponse(String next) {
        this.setNext(next);
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public CursorResponse withNext(String next) {
        this.next = next;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("next", next)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(next)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CursorResponse)) {
            return false;
        }

        CursorResponse rhs = ((CursorResponse) other);
        return new EqualsBuilder()
                .append(next, rhs.next)
                .isEquals();
    }
}
