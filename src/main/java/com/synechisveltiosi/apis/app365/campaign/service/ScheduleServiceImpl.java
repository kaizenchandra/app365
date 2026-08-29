package com.synechisveltiosi.apis.app365.campaign.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.campaign.ScheduleNotFoundException;
import com.synechisveltiosi.apis.app365.campaign.entity.Schedule;
import com.synechisveltiosi.apis.app365.campaign.entity.Task;
import com.synechisveltiosi.apis.app365.campaign.repository.ScheduleRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.users.event.UserActionOccurredEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ApplicationEventPublisher publisher) {
        this.scheduleRepository = scheduleRepository;
        this.publisher = publisher;
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Schedule id should not be null or 0");

        return scheduleRepository.findById(id);
    }

    @Override
    public Optional<Schedule> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Schedule id should not be null or blank");

        return scheduleRepository.findByScheduleId(id);
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> findAllByVolunteerId(Long volunteerId) {
        if (volunteerId == null || volunteerId == 0)
            throw new BadRequestException("Volunteer id should not be null or 0");

        return scheduleRepository.findAllByVolunteerId_Id(volunteerId);
    }

    @Override
    public List<Schedule> findAllByVolunteerIdAndTaskId(Long volunteerId, String taskId) {
        if (volunteerId == null || volunteerId == 0)
            throw new BadRequestException("Volunteer id should not be null or 0");

        if (StringUtils.isBlank(taskId)) throw new BadRequestException("Task id should not be null or blank");

        return scheduleRepository.findAllByVolunteerId_IdAndTaskId_TaskIdOrderByStartTimeAsc(volunteerId, taskId);
    }

    @Override
    public List<Task> findAllTasksByVolunteerId(Long volunteerId) {
        if (volunteerId == null || volunteerId == 0)
            throw new BadRequestException("Volunteer id should not be null or 0");

        return scheduleRepository.findAllTasksForVolunteer(volunteerId);
    }

    @Transactional
    @Override
    public void save(List<Schedule> schedules) {
        scheduleRepository.saveAll(schedules);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Schedule id should not be null or 0");

        Optional<Schedule> scheduleOptional = findById(id);
        if (!scheduleOptional.isPresent()) throw new ScheduleNotFoundException();

        scheduleRepository.deleteById(id);

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(
                scheduleOptional.get().getVolunteerId().getUserId().getId(), ActionType.VOLUNTEER_SCHEDULE,
                ActionType.MEMBER, ActionType.LEVEL));
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Schedule id should not be null or blank");

        Optional<Schedule> scheduleOptional = findById(id);
        if (!scheduleOptional.isPresent()) throw new ScheduleNotFoundException();

        scheduleRepository.deleteById(scheduleOptional.get().getId());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(
                scheduleOptional.get().getVolunteerId().getUserId().getId(), ActionType.VOLUNTEER_SCHEDULE,
                ActionType.MEMBER, ActionType.LEVEL));
    }

    @Transactional
    @Override
    public void deleteByVolunteerIdAndTaskId(Long volunteerId, Long taskId) {
        if (volunteerId == null || volunteerId == 0)
            throw new BadRequestException("Volunteer id should not be null or 0");
        if (taskId == null || taskId == 0) throw new BadRequestException("Task id should not be null or 0");

        List<Schedule> schedules = findAllByVolunteerId(volunteerId);
        if (schedules.isEmpty()) throw new ScheduleNotFoundException();

        scheduleRepository.deleteByVolunteerId_IdAndTaskId_Id(volunteerId, taskId);

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(
                schedules.get(0).getVolunteerId().getUserId().getId(), ActionType.VOLUNTEER_SCHEDULE,
                ActionType.MEMBER, ActionType.LEVEL));
    }
}
