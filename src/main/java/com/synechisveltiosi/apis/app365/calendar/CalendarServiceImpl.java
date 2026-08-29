package com.synechisveltiosi.apis.app365.calendar;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarServiceImpl(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @Override
    public Optional<Calendar> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Event id should not be null or 0");

        return calendarRepository.findById(id);
    }

    @Override
    public Optional<Calendar> findByUserIdAndEventId(Long userId, String eventId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");

        return calendarRepository.findByUserId_IdAndEventId_EventId(userId, eventId);
    }

    @Override
    public Calendar save(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    @Override
    public void deleteByUserIdAndEventId(Long userId, String eventId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");
        if (StringUtils.isBlank(eventId)) throw new BadRequestException("Event id should not be null or blank");

        Optional<Calendar> calendarOptional = this.findByUserIdAndEventId(userId, eventId);
        if (!calendarOptional.isPresent()) throw new CalendarEntryNotFoundException();

        calendarRepository.deleteByUserId_IdAndEventId_EventId(userId, eventId);
    }
}
