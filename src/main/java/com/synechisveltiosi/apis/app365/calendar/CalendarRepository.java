package com.synechisveltiosi.apis.app365.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Optional<Calendar> findByUserId_IdAndEventId_EventId(Long userId, String eventId);

    void deleteByUserId_IdAndEventId_EventId(Long userId, String eventId);
}
