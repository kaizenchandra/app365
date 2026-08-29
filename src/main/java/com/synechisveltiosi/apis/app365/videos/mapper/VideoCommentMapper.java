package com.synechisveltiosi.apis.app365.videos.mapper;

import com.synechisveltiosi.apis.app365.common.dto.comments.CommentRequest;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.videos.entity.VideoComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VideoCommentMapper {

    VideoCommentMapper INSTANCE = Mappers.getMapper(VideoCommentMapper.class);

    @Mappings({
            @Mapping(source = "commentId", target = "id"),
            @Mapping(source = "userId", target = "owner")
    })
    CommentResponse from(VideoComment comment);

    VideoComment from(CommentRequest commentRequest);
}
