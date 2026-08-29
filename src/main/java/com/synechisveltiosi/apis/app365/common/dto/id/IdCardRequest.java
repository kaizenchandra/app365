
package com.synechisveltiosi.apis.app365.common.dto.id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdCardRequest {

    @NotBlank
    @JsonProperty("idCard")
    private String idCard;

    public String getId() {
        return idCard;
    }

    public void setId(String idCard) {
        this.idCard = idCard;
    }

    public IdCardRequest withId(String idCard) {
        this.idCard = idCard;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("idCard", idCard)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(idCard)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IdCardRequest)) {
            return false;
        }

        IdCardRequest rhs = ((IdCardRequest) other);
        return new EqualsBuilder()
                .append(idCard, rhs.idCard)
                .isEquals();
    }
}
