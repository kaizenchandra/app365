package com.synechisveltiosi.apis.app365.calendar.mapper;

import com.synechisveltiosi.apis.app365.calendar.Calendar;
import com.synechisveltiosi.apis.app365.calendar.dto.CalendarEntryRequest;
import com.synechisveltiosi.apis.app365.calendar.dto.CalendarResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CalendarMapper {

    CalendarMapper INSTANCE = Mappers.getMapper(CalendarMapper.class);

    Calendar from(CalendarEntryRequest calendarEntryRequest);

    CalendarResponse from(Calendar calendar);
}
