package com.synechisveltiosi.apis.app365.calendar.dto;

import java.util.ArrayList;
import java.util.List;

public class OverlappingScheduleResponse {

    private Integer day;
    private List<IntervalResponse> schedules;

    public OverlappingScheduleResponse() {

    }

    public OverlappingScheduleResponse(Integer day) {
        this.day = day;
    }

    public OverlappingScheduleResponse(Integer day, List<IntervalResponse> schedules) {
        this.day = day;
        this.schedules = schedules;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public List<IntervalResponse> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<IntervalResponse> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(IntervalResponse interval) {
        if (schedules == null) schedules = new ArrayList<>();

        schedules.add(interval);
    }
}
