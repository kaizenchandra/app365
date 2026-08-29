package com.synechisveltiosi.apis.app365.common.util.date;

import java.util.ArrayList;
import java.util.List;

public class OverlappingSchedule {

    private List<Interval> schedules;

    public OverlappingSchedule() {

    }

    public OverlappingSchedule(List<Interval> schedules) {
        this.schedules = schedules;
    }

    public List<Interval> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Interval> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(Interval interval) {
        if (schedules == null) schedules = new ArrayList<>();

        schedules.add(interval);
    }
}
