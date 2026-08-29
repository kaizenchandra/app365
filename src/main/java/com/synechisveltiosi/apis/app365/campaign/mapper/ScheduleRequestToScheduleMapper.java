package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.calendar.dto.ScheduleRequest;
import com.synechisveltiosi.apis.app365.campaign.entity.Schedule;
import com.synechisveltiosi.apis.app365.campaign.entity.Task;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.util.date.DateConverter;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScheduleRequestToScheduleMapper {

    public static List<Schedule> map(ScheduleRequest scheduleRequest) {
        if (scheduleRequest == null)
            throw new BadRequestException("Schedule cannot be null.");

        final List<Schedule> schedules = new ArrayList<>();

        Optional.ofNullable(scheduleRequest.getEntries())
                .orElseThrow(() -> new BadRequestException("Schedule entry cannot be null"))
                .forEach(scheduleEntryRequest -> {
                    // Create the task
                    Task task = new Task();
                    task.setTaskId(scheduleRequest.getTaskId());

                    // Create the schedule
                    Schedule schedule = new Schedule();
                    schedule.setTaskId(task);
                    schedule.setNote(scheduleRequest.getNote());
                    schedule.setStartTime(DateConverter.fromDate(scheduleEntryRequest.getStartTime()));
                    schedule.setEndTime(DateConverter.fromDate(scheduleEntryRequest.getEndTime()));
                    schedule.setRepeatDays(scheduleEntryRequest.getRepeatDays());

                    if (scheduleEntryRequest.getRepeatUntil() != null) {
                        schedule.setRepeatUntil(scheduleEntryRequest.getRepeatUntil().toInstant()
                                .atZone(ZoneId.systemDefault()).toLocalDate());
                    }

                    schedules.add(schedule);
                });

        return schedules;
    }
}
