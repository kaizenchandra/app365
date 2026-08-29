package com.synechisveltiosi.apis.app365.actions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AwardsResponse {

    @JsonProperty("points")
    private Integer points;

    @JsonProperty("medals")
    private List<MedalResponse> medals = new ArrayList<MedalResponse>();

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public AwardsResponse withPoints(Integer points) {
        this.points = points;
        return this;
    }

    public List<MedalResponse> getMedals() {
        return medals;
    }

    public void setMedals(List<MedalResponse> medals) {
        this.medals = medals;
    }

    public AwardsResponse withMedals(List<MedalResponse> medals) {
        this.medals = medals;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("points", points)
                .append("medals", medals)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(medals)
                .append(points)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AwardsResponse)) {
            return false;
        }

        AwardsResponse rhs = ((AwardsResponse) other);
        return new EqualsBuilder()
                .append(medals, rhs.medals)
                .append(points, rhs.points)
                .isEquals();
    }
}
