package com.synechisveltiosi.apis.app365.events.mapper;

import com.synechisveltiosi.apis.app365.events.dto.EventResponse;
import com.synechisveltiosi.apis.app365.events.entity.Event;
import com.synechisveltiosi.apis.app365.location.LocationMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LocationMapper.class)
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mappings({
            @Mapping(source = "eventId", target = "id"),
            @Mapping(source = "eventSummary", target = "summary"),
            @Mapping(source = "calendars", target = "meta.user.calendars")
    })
    EventResponse from(Event event);
}
