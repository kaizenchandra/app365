package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.calendar.dto.ScheduleEntryResponse;
import com.synechisveltiosi.apis.app365.calendar.dto.ScheduleResponse;
import com.synechisveltiosi.apis.app365.campaign.dto.TaskResponse;
import com.synechisveltiosi.apis.app365.campaign.entity.Schedule;
import com.synechisveltiosi.apis.app365.common.util.date.DateConverter;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ScheduleToScheduleResponseMapper extends AbstractMapper<Schedule, ScheduleResponse> {

    @Override
    public ScheduleResponse map(Schedule schedule) {
        return ScheduleMapper.INSTANCE.from(schedule);
    }

    public static ScheduleResponse mapForSingleTask(List<Schedule> schedules) {
        if (schedules == null || schedules.isEmpty()) return null;

        ScheduleResponse scheduleResponse = new ScheduleResponse();
        schedules.forEach(schedule -> {
            // Set the task
            scheduleResponse.setTask(new TaskResponse()
                    .withId(schedule.getTaskId().getTaskId())
                    .withName(schedule.getTaskId().getName()));

            // Create schedule entry object if not exist yet
            if (scheduleResponse.getEntries() == null) scheduleResponse.setEntries(new ArrayList<>());

            // Add schedule entries
            scheduleResponse.getEntries().add(new ScheduleEntryResponse()
                    .withId(schedule.getScheduleId())
                    .withNote(schedule.getNote())
                    .withStartTime(DateConverter.toDate(schedule.getStartTime()))
                    .withEndTime(DateConverter.toDate(schedule.getEndTime()))
                    .withRepeatDays(schedule.getRepeatDays())
                    .withRepeatUntil(schedule.getRepeatUntil() != null
                            ? Date.from(schedule.getRepeatUntil().atStartOfDay().atZone(
                            ZoneId.systemDefault()).toInstant())
                            : null));
        });

        return scheduleResponse;
    }
}
