package com.synechisveltiosi.apis.app365.campaign.helper;

import com.synechisveltiosi.apis.app365.campaign.entity.Schedule;
import com.synechisveltiosi.apis.app365.common.util.date.Interval;
import com.synechisveltiosi.apis.app365.common.util.date.OverlappingSchedule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ScheduleHelper {

    /**
     * Detect overlapping in schedules
     *
     * @param schedules
     * @return
     */
    public static List<OverlappingSchedule> getOverlapping(final List<Schedule> schedules) {
        final List<OverlappingSchedule> overlaps = new ArrayList<>();
        if (schedules == null) return overlaps;

        // Classify schedule on a per day basis
        final List<Interval> daySchedules = new ArrayList<>();
        schedules.forEach(schedule -> {
            // Copy time copy
            LocalDateTime startTime = LocalDateTime.of(schedule.getStartTime().toLocalDate(),
                    schedule.getStartTime().toLocalTime());

            LocalDateTime endTime = LocalDateTime.of(schedule.getEndTime().toLocalDate(),
                    schedule.getEndTime().toLocalTime());

            // Calculate duration in seconds
            long seconds = Duration.between(LocalDateTime.of(startTime.toLocalDate(), startTime.toLocalTime()),
                    LocalDateTime.of(startTime.toLocalDate(), schedule.getEndTime().toLocalTime())).getSeconds();

            // Iterate through the time range to create schedule for each day
            while (!startTime.isAfter(schedule.getEndTime())) {


//                LocalDateTime dayEndTime = LocalDateTime.of();

                // Create interval
                Interval interval = new Interval(LocalDateTime.of(startTime.toLocalDate(), startTime.toLocalTime()),
                        LocalDateTime.of(startTime.toLocalDate(), schedule.getEndTime().toLocalTime()));

                // Add the interval
                daySchedules.add(interval);

                // Increment the date
                startTime = startTime.plusDays(1);
            }
        });

        // Determine overlapping for each schedule entry
        for (int index = 1; index < daySchedules.size(); index++) {
            Interval left = daySchedules.get(index - 1);
            Interval right = daySchedules.get(index);
            if (right.isOverlapping(left)) {
                // Add overlapping
                overlaps.add(new OverlappingSchedule(Arrays.asList(new Interval(left.getStartTime(), left.getEndTime()),
                        new Interval(right.getStartTime(), right.getEndTime()))));
            }
        }

        return overlaps;
    }
}
