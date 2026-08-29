package com.synechisveltiosi.apis.app365.events.mapper;

import com.synechisveltiosi.apis.app365.common.dto.comments.CommentRequest;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.events.entity.EventComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventCommentMapper {

    EventCommentMapper INSTANCE = Mappers.getMapper(EventCommentMapper.class);

    @Mappings({
            @Mapping(source = "commentId", target = "id"),
            @Mapping(source = "userId", target = "owner")
    })
    CommentResponse from(EventComment comment);

    EventComment from(CommentRequest commentRequest);
}
