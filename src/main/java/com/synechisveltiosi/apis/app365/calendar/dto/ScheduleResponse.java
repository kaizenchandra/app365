
package com.synechisveltiosi.apis.app365.calendar.dto;

import com.synechisveltiosi.apis.app365.campaign.dto.TaskResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponse {

    @JsonProperty("task")
    private TaskResponse task;

    @JsonProperty("entries")
    private List<ScheduleEntryResponse> entries;

    public TaskResponse getTask() {
        return task;
    }

    public void setTask(TaskResponse task) {
        this.task = task;
    }

    public ScheduleResponse withTask(TaskResponse task) {
        this.task = task;
        return this;
    }

    public List<ScheduleEntryResponse> getEntries() {
        return entries;
    }

    public void setEntries(List<ScheduleEntryResponse> entries) {
        this.entries = entries;
    }

    public ScheduleResponse withEntries(List<ScheduleEntryResponse> entries) {
        this.entries = entries;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("task", task)
                .append("entries", entries)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(task)
                .append(entries)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ScheduleResponse)) {
            return false;
        }

        ScheduleResponse rhs = ((ScheduleResponse) other);
        return new EqualsBuilder()
                .append(task, rhs.task)
                .append(entries, rhs.entries)
                .isEquals();
    }
}
