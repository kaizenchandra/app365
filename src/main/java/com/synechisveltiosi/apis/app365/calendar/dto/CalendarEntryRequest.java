package com.synechisveltiosi.apis.app365.calendar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.common.util.Platform;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalendarEntryRequest {

    @JsonProperty("platform")
    private Platform platform;

    @JsonProperty("calendarId")
    private String calendarId;

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public CalendarEntryRequest withPlatform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public CalendarEntryRequest withCalendarId(String calendarId) {
        this.calendarId = calendarId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("platform", platform)
                .append("calendarId", calendarId)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(calendarId)
                .append(platform)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CalendarEntryRequest)) {
            return false;
        }

        CalendarEntryRequest rhs = ((CalendarEntryRequest) other);
        return new EqualsBuilder()
                .append(calendarId, rhs.calendarId)
                .append(platform, rhs.platform)
                .isEquals();
    }
}
