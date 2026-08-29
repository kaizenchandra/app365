package com.synechisveltiosi.apis.app365.events.mapper;

import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.events.dto.EventResponse;
import com.synechisveltiosi.apis.app365.events.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventToEventResponseMapper extends AbstractMapper<Event, EventResponse> {

    @Override
    public EventResponse map(Event event) {
        return EventMapper.INSTANCE.from(event);
    }
}
