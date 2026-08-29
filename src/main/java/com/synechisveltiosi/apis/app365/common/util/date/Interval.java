package com.synechisveltiosi.apis.app365.common.util.date;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.time.LocalDateTime;

public final class Interval {

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Interval() {

    }

    public Interval(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public boolean isValid() {
        return startTime != null && endTime != null && !startTime.equals(endTime) && startTime.isBefore(endTime);
    }

    @JsonIgnore
    public boolean isOverlapping(Interval other) {
        return (other == null || !this.isValid() || !other.isValid())
                || (this.getStartTime().isBefore(other.getEndTime())
                && other.getStartTime().isBefore(this.getEndTime()));
    }
}
