package com.synechisveltiosi.apis.app365.events.mapper;

import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.events.entity.EventComment;
import org.springframework.stereotype.Component;

@Component
public class EventCommentToCommentResponseMapper extends AbstractMapper<EventComment, CommentResponse> {

    @Override
    public CommentResponse map(EventComment eventComment) {
        return EventCommentMapper.INSTANCE.from(eventComment);
    }
}
