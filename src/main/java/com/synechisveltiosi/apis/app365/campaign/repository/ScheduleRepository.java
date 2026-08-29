package com.synechisveltiosi.apis.app365.campaign.repository;

import com.synechisveltiosi.apis.app365.campaign.entity.Schedule;
import com.synechisveltiosi.apis.app365.campaign.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByScheduleId(String id);

    List<Schedule> findAllByVolunteerId_Id(Long volunteerId);

    List<Schedule> findAllByVolunteerId_IdAndTaskId_TaskIdOrderByStartTimeAsc(Long volunteerId, String taskId);

    @Query("select s.taskId from Schedule s where s.volunteerId.id = :volunteerId group by s.taskId.id")
    List<Task> findAllTasksForVolunteer(@Param("volunteerId") Long volunteerId);

    void deleteByVolunteerId_IdAndTaskId_Id(Long volunteerId, Long taskId);
}
