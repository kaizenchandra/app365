package com.synechisveltiosi.apis.app365.calendar.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntervalResponse {

    @JsonProperty("startTime")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_TIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date startTime;

    @JsonProperty("endTime")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_TIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date endTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public IntervalResponse withStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public IntervalResponse withEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(startTime)
                .append(endTime)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof IntervalResponse)) {
            return false;
        }

        IntervalResponse rhs = ((IntervalResponse) other);
        return new EqualsBuilder()
                .append(startTime, rhs.startTime)
                .append(endTime, rhs.endTime)
                .isEquals();
    }
}
