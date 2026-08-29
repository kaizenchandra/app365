package com.synechisveltiosi.apis.app365.users.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechisveltiosi.apis.app365.calendar.dto.CalendarResponse;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserActionMetaResponse {

    @JsonProperty("liked")
    private Boolean liked;

    @JsonProperty("commented")
    private Boolean commented;

    @JsonProperty("shared")
    private Boolean shared;

    @JsonProperty("joined")
    private Boolean joined;

    @JsonProperty("calendars")
    private List<CalendarResponse> calendars;

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public UserActionMetaResponse withLiked(Boolean liked) {
        this.liked = liked;
        return this;
    }

    public Boolean getCommented() {
        return commented;
    }

    public void setCommented(Boolean commented) {
        this.commented = commented;
    }

    public UserActionMetaResponse withCommented(Boolean commented) {
        this.commented = commented;
        return this;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public UserActionMetaResponse withShared(Boolean shared) {
        this.shared = shared;
        return this;
    }

    public Boolean getJoined() {
        return joined;
    }

    public void setJoined(Boolean joined) {
        this.joined = joined;
    }

    public UserActionMetaResponse withJoined(Boolean joined) {
        this.joined = joined;
        return this;
    }

    public List<CalendarResponse> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<CalendarResponse> calendars) {
        this.calendars = calendars;
    }

    public UserActionMetaResponse withCalendars(List<CalendarResponse> calendar) {
        this.calendars = calendar;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("liked", liked)
                .append("commented", commented)
                .append("shared", shared)
                .append("joined", joined)
                .append("calendars", calendars)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(calendars)
                .append(shared)
                .append(commented)
                .append(liked)
                .append(joined)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UserActionMetaResponse)) {
            return false;
        }

        UserActionMetaResponse rhs = ((UserActionMetaResponse) other);
        return new EqualsBuilder()
                .append(calendars, rhs.calendars)
                .append(shared, rhs.shared)
                .append(commented, rhs.commented)
                .append(liked, rhs.liked)
                .append(joined, rhs.joined)
                .isEquals();
    }
}
