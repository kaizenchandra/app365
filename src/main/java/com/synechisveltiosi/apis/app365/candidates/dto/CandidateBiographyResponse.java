
package com.synechisveltiosi.apis.app365.candidates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CandidateBiographyResponse {

    @JsonProperty("description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CandidateBiographyResponse withDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("description", description)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(description)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CandidateBiographyResponse)) {
            return false;
        }

        CandidateBiographyResponse rhs = ((CandidateBiographyResponse) other);
        return new EqualsBuilder()
                .append(description, rhs.description)
                .isEquals();
    }
}
