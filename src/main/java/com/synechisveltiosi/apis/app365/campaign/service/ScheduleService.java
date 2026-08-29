package com.synechisveltiosi.apis.app365.campaign.service;

import com.synechisveltiosi.apis.app365.campaign.entity.Schedule;
import com.synechisveltiosi.apis.app365.campaign.entity.Task;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    Optional<Schedule> findById(Long id);

    Optional<Schedule> findById(String id);

    List<Schedule> findAll();

    List<Schedule> findAllByVolunteerId(Long volunteerId);

    List<Schedule> findAllByVolunteerIdAndTaskId(Long volunteerId, String taskId);

    List<Task> findAllTasksByVolunteerId(Long volunteerId);

    void save(List<Schedule> schedules);

    void deleteById(Long id);

    void deleteById(String id);

    void deleteByVolunteerIdAndTaskId(Long volunteerId, Long taskId);
}
