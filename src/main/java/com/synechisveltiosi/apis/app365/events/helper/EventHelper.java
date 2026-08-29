package com.synechisveltiosi.apis.app365.events.helper;

import com.synechisveltiosi.apis.app365.common.dto.DefaultMetaResponse;
import com.synechisveltiosi.apis.app365.events.dto.EventResponse;
import com.synechisveltiosi.apis.app365.events.entity.Event;
import com.synechisveltiosi.apis.app365.events.mapper.EventCommentMapper;
import com.synechisveltiosi.apis.app365.events.mapper.EventMapper;
import com.synechisveltiosi.apis.app365.users.dto.UserActionMetaResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class EventHelper {

    /**
     * Inject user meta data
     *
     * @param userId
     * @param events
     * @return
     */
    public static List<EventResponse> processEventUserHasJoined(Long userId, List<Event> events) {
        return events.stream()
                .map(event -> processEventUserHasJoined(userId, event))
                .collect(Collectors.toList());
    }

    public static EventResponse processEventUserHasJoined(Long userId, Event event) {
        EventResponse eventResponse = EventMapper.INSTANCE.from(event);

        // Mark user joins
        markUserJoins(userId, event, eventResponse);

        // Mark user likes
        markUserLikes(userId, event, eventResponse);

        // Mark user share
        markUserShare(userId, event, eventResponse);

        // Map de last comment
        mapLastComment(event, eventResponse);

        return eventResponse;
    }

    @SuppressWarnings("Duplicates")
    public static List<EventResponse> nullifyListOnlyFields(List<EventResponse> events) {
        return events.stream()
                .peek(event -> {
                    event.setLocation(null);
                    event.setSummary(null);
                    event.setCursor(null);

                    if (event.getMeta() != null) {
                        event.getMeta().setLastComment(null);

                        UserActionMetaResponse userActionMetaResponse = event.getMeta().getUser();
                        if (userActionMetaResponse != null) {
                            userActionMetaResponse.setCalendars(null);
                            userActionMetaResponse.setCommented(null);
                            userActionMetaResponse.setLiked(null);
                            userActionMetaResponse.setShared(null);

                            // If no join metadata, reset the meta as we only care about this field
                            if (userActionMetaResponse.getJoined() == null) {
                                event.setMeta(null);
                            }
                        }
                    }

                })
                .collect(Collectors.toList());
    }

    @SuppressWarnings("Duplicates")
    private static void markUserShare(Long userId, Event event, EventResponse eventResponse) {
        if (!event.getShares().isEmpty()) {
            // If the user is found that means he shared this event already, add the share flag
            event.getShares().stream()
                    .filter(eventShare -> Objects.equals(eventShare.getUserId().getId(), userId))
                    .forEach(eventShare -> {
                        if (eventResponse.getMeta() == null)
                            eventResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
                        eventResponse.getMeta().getUser().withShared(Boolean.TRUE);
                    });
        }
    }

    @SuppressWarnings("Duplicates")
    private static void markUserLikes(Long userId, Event event, EventResponse eventResponse) {
        if (!event.getLikes().isEmpty()) {
            // If I liked this event already, add the like flag
            event.getLikes().stream()
                    .filter(eventLike -> Objects.equals(eventLike.getUserId().getId(), userId))
                    .forEach(eventLike -> {
                        if (eventResponse.getMeta() == null)
                            eventResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
                        eventResponse.getMeta().getUser().withLiked(eventLike.getLiked());
                    });
        }
    }

    @SuppressWarnings("Duplicates")
    private static void markUserJoins(Long userId, Event event, EventResponse eventResponse) {
        // Mark event join to false by default
        if (eventResponse.getMeta() == null)
            eventResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
        eventResponse.getMeta().getUser().withJoined(Boolean.FALSE);

        if (!event.getJoinEvents().isEmpty()) {
            // If I joined this event already, add the join flag
            event.getJoinEvents().stream()
                    .filter(joinEvent -> Objects.equals(joinEvent.getUserId().getId(), userId))
                    .forEach(joinEvent -> {
                        eventResponse.getMeta().getUser().withJoined(Boolean.TRUE);
                    });
        }
    }

    @SuppressWarnings("Duplicates")
    private static void mapLastComment(Event event, EventResponse eventResponse) {
        if (event.getLastComment() != null) {
            if (eventResponse.getMeta() == null)
                eventResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
            eventResponse.getMeta().setLastComment(EventCommentMapper.INSTANCE.from(event.getLastComment()));
        }
    }
}