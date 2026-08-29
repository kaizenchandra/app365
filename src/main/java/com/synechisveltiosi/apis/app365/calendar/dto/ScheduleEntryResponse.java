
package com.synechisveltiosi.apis.app365.calendar.dto;

import com.synechisveltiosi.apis.app365.common.util.date.DateFormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleEntryResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("startTime")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date startTime;

    @JsonProperty("endTime")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date endTime;

    @JsonProperty("repeatUntil")
    @JsonFormat(pattern = DateFormatUtils.ISO_8601_DATE_STRING_FORMAT, shape = JsonFormat.Shape.STRING)
    private Date repeatUntil;

    @JsonProperty("repeatDays")
    private List<Integer> repeatDays;

    @JsonProperty("note")
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ScheduleEntryResponse withId(String id) {
        this.id = id;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public ScheduleEntryResponse withStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ScheduleEntryResponse withEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ScheduleEntryResponse withNote(String note) {
        this.note = note;
        return this;
    }

    public Date getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(Date repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public ScheduleEntryResponse withRepeatUntil(Date repeatUntil) {
        this.repeatUntil = repeatUntil;
        return this;
    }

    public List<Integer> getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(List<Integer> repeatDays) {
        this.repeatDays = repeatDays;
    }

    public ScheduleEntryResponse withRepeatDays(List<Integer> repeatDays) {
        this.repeatDays = repeatDays;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("startTime", startTime)
                .append("endTime", endTime)
                .append("repeatUntil", repeatUntil)
                .append("repeatDays", repeatDays)
                .append("note", note)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ScheduleEntryResponse that = (ScheduleEntryResponse) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(startTime, that.startTime)
                .append(endTime, that.endTime)
                .append(repeatUntil, that.repeatUntil)
                .append(repeatDays, that.repeatDays)
                .append(note, that.note)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(startTime)
                .append(endTime)
                .append(repeatUntil)
                .append(repeatDays)
                .append(note)
                .toHashCode();
    }
}
