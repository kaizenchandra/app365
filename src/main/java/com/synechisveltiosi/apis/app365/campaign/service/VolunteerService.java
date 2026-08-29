package com.synechisveltiosi.apis.app365.campaign.service;

import com.synechisveltiosi.apis.app365.campaign.entity.Schedule;
import com.synechisveltiosi.apis.app365.campaign.entity.Task;
import com.synechisveltiosi.apis.app365.campaign.entity.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VolunteerService {

    Optional<Volunteer> findById(Long id);

    Optional<Volunteer> findByUserId(Long userId);

    Page<Volunteer> findAll(String query, Pageable pageable);

    Volunteer save(Volunteer volunteer);

    List<Schedule> findAllSchedulesByUserId(Long userId);

    List<Schedule> findAllSchedulesByUserIdAndTaskId(Long userId, String taskId);

    List<Task> findAllTasksByUserId(Long userId);

    void addVolunteerSchedules(Long userId, List<Schedule> schedules);

    void deleteByUserId(Long userId);

    void deleteByUserIdAndTaskId(Long userId, String taskId);
}
