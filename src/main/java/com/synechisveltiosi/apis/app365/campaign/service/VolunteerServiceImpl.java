package com.synechisveltiosi.apis.app365.campaign.service;

import com.synechisveltiosi.apis.app365.actions.entity.ActionType;
import com.synechisveltiosi.apis.app365.calendar.dto.OverlappingScheduleResponse;
import com.synechisveltiosi.apis.app365.campaign.ScheduleDuplicatedException;
import com.synechisveltiosi.apis.app365.campaign.TaskNotFoundException;
import com.synechisveltiosi.apis.app365.campaign.VolunteerNotFoundException;
import com.synechisveltiosi.apis.app365.campaign.entity.Schedule;
import com.synechisveltiosi.apis.app365.campaign.entity.Task;
import com.synechisveltiosi.apis.app365.campaign.entity.Volunteer;
import com.synechisveltiosi.apis.app365.campaign.helper.ScheduleHelper;
import com.synechisveltiosi.apis.app365.campaign.repository.VolunteerRepository;
import com.synechisveltiosi.apis.app365.common.repository.DefaultRsqlRepository;
import com.synechisveltiosi.apis.app365.common.repository.RsqlRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.util.date.OverlappingSchedule;
import com.synechisveltiosi.apis.app365.common.util.mapper.Mapper;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.event.UserActionOccurredEvent;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final RsqlRepository<Volunteer> rsqlRepository;
    private final TaskService taskService;
    private final ScheduleService scheduleService;
    private final UserService userService;
    private final Mapper<OverlappingSchedule, OverlappingScheduleResponse> overlappingScheduleResponseMapper;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public VolunteerServiceImpl(
            VolunteerRepository volunteerRepository, TaskService taskService,
            ScheduleService scheduleService, UserService userService,
            Mapper<OverlappingSchedule, OverlappingScheduleResponse> overlappingScheduleResponseMapper,
            ApplicationEventPublisher publisher, EntityManager entityManager) {

        this.volunteerRepository = volunteerRepository;
        this.taskService = taskService;
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.overlappingScheduleResponseMapper = overlappingScheduleResponseMapper;
        this.publisher = publisher;
        rsqlRepository = new DefaultRsqlRepository<>(entityManager, Volunteer.class)
                .withAllowedFields(Volunteer.SEARCHABLE_FIELDS);
    }

    @Override
    public Optional<Volunteer> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Volunteer id should not be null or 0");

        return volunteerRepository.findById(id);
    }

    @Override
    public Optional<Volunteer> findByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return volunteerRepository.findByUserId_IdAndActiveIsTrue(userId);
    }

    @Override
    public Page<Volunteer> findAll(String query, Pageable pageable) {
        pageable = pageable == null ? Pageable.unpaged() : pageable;

        if (StringUtils.isEmpty(query))
            return volunteerRepository.findAllByActiveIsTrue(pageable);

        return rsqlRepository.findAll(query, pageable);
    }

    @Transactional
    @Override
    public Volunteer save(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    public List<Schedule> findAllSchedulesByUserId(Long userId) {
        Optional<Volunteer> volunteerOptional = findByUserId(userId);
        if (!volunteerOptional.isPresent()) throw new VolunteerNotFoundException();

        return scheduleService.findAllByVolunteerId(volunteerOptional.get().getId());
    }

    @Override
    public List<Schedule> findAllSchedulesByUserIdAndTaskId(Long userId, String taskId) {
        Optional<Volunteer> volunteerOptional = findByUserId(userId);
        if (!volunteerOptional.isPresent()) throw new VolunteerNotFoundException();

        return scheduleService.findAllByVolunteerIdAndTaskId(volunteerOptional.get().getId(), taskId);
    }

    @Override
    public List<Task> findAllTasksByUserId(Long userId) {
        Optional<Volunteer> volunteerOptional = findByUserId(userId);
        if (!volunteerOptional.isPresent()) throw new VolunteerNotFoundException();

        return scheduleService.findAllTasksByVolunteerId(volunteerOptional.get().getId());
    }

    @Transactional
    @Override
    public void addVolunteerSchedules(Long userId, List<Schedule> schedules) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (schedules == null || schedules.isEmpty()) throw new BadRequestException("No schedules specified.");

        // Validate that the user exist
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Find an active volunteer registration
        Optional<Volunteer> volunteerOptional = findByUserId(userId);

        // This user is not a volunteer yet, add him
        if (!volunteerOptional.isPresent()) {
            Volunteer volunteer = new Volunteer();
            volunteer.setActive(Boolean.TRUE);
            volunteer.setUserId(userOptional.get());

            // Save this volunteer
            volunteerOptional = Optional.of(this.save(volunteer));
        }

        // Link schedule with the user
        final Volunteer volunteer = volunteerOptional.get();
        schedules.forEach(schedule -> schedule.setVolunteerId(volunteer));

        // Add managed task for the schedule
        schedules.forEach(schedule -> {
            String taskId = schedule.getTaskId().getTaskId();

            // Search the task TODO Cache the task so we don't make a query every time for the same task
            Optional<Task> taskOptional = taskService.findById(taskId);
            if (!taskOptional.isPresent()) throw new TaskNotFoundException();

            // Set the task
            schedule.setTaskId(taskOptional.get());
        });

        // Check overlapping
        List<Schedule> oldSchedule = scheduleService.findAllByVolunteerId(volunteer.getId());
        oldSchedule.addAll(schedules);
        List<OverlappingSchedule> overlappingSchedules = ScheduleHelper.getOverlapping(oldSchedule);
        if (!overlappingSchedules.isEmpty()) {
            ScheduleDuplicatedException exception = new ScheduleDuplicatedException();
            exception.setAdditionalErrors(overlappingScheduleResponseMapper.map(overlappingSchedules));

//            throw exception; // TODO Enable overlapping check
        }

        // Save the schedules
        scheduleService.save(schedules);

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.VOLUNTEER_SCHEDULE,
                ActionType.MEMBER, ActionType.LEVEL));
    }

    @Transactional
    @Override
    public void deleteByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        Optional<Volunteer> volunteerOptional = findByUserId(userId);
        if (!volunteerOptional.isPresent()) throw new VolunteerNotFoundException();

        // Mark the volunteer as inactive
        Volunteer volunteer = volunteerOptional.get();
        volunteer.setActive(Boolean.FALSE);

        // Save the volunteer state
        this.save(volunteer);

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.VOLUNTEER_SCHEDULE,
                ActionType.MEMBER, ActionType.LEVEL));
    }

    @Override
    public void deleteByUserIdAndTaskId(Long userId, String taskId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (StringUtils.isBlank(taskId)) throw new BadRequestException("Task id should not be null or blank");

        // Validate the user is volunteered to this campaign
        Optional<Volunteer> volunteerOptional = findByUserId(userId);
        if (!volunteerOptional.isPresent()) throw new VolunteerNotFoundException();

        // Validate this task exist
        Optional<Task> taskOptional = taskService.findById(taskId);
        if (!taskOptional.isPresent()) throw new TaskNotFoundException();

        // Remove all schedule for this volunteer for a particular task
        scheduleService.deleteByVolunteerIdAndTaskId(volunteerOptional.get().getId(), taskOptional.get().getId());

        //Notify observers about the user action
        publisher.publishEvent(new UserActionOccurredEvent(userId, ActionType.VOLUNTEER_SCHEDULE,
                ActionType.MEMBER, ActionType.LEVEL));
    }
}
