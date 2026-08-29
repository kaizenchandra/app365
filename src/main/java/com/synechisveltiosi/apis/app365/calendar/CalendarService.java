package com.synechisveltiosi.apis.app365.calendar;

import java.util.Optional;

public interface CalendarService {

    Optional<Calendar> findById(Long id);

    Optional<Calendar> findByUserIdAndEventId(Long userId, String eventId);

    Calendar save(Calendar calendar);

    void deleteByUserIdAndEventId(Long userId, String eventId);
}
