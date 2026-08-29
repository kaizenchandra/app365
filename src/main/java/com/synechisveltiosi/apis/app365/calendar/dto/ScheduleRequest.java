package com.synechisveltiosi.apis.app365.calendar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleRequest {

    @JsonProperty("taskId")
    private String taskId;

    @JsonProperty("note")
    private String note;

    @JsonProperty("entries")
    private List<ScheduleEntryRequest> entries;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ScheduleRequest withTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ScheduleRequest withNote(String note) {
        this.note = note;
        return this;
    }

    public List<ScheduleEntryRequest> getEntries() {
        return entries;
    }

    public void setEntries(List<ScheduleEntryRequest> entries) {
        this.entries = entries;
    }

    public ScheduleRequest withEntries(List<ScheduleEntryRequest> entries) {
        this.entries = entries;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("note", note)
                .append("taskId", taskId)
                .append("entries", entries)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(note)
                .append(taskId)
                .append(entries)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ScheduleRequest)) {
            return false;
        }

        ScheduleRequest rhs = ((ScheduleRequest) other);
        return new EqualsBuilder()
                .append(note, rhs.note)
                .append(taskId, rhs.taskId)
                .append(entries, rhs.entries)
                .isEquals();
    }
}
