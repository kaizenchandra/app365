package com.synechisveltiosi.apis.app365.news.mapper;

import com.synechisveltiosi.apis.app365.common.dto.comments.CommentRequest;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.news.entity.NewsComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsCommentMapper {

    NewsCommentMapper INSTANCE = Mappers.getMapper(NewsCommentMapper.class);

    @Mappings({
            @Mapping(source = "commentId", target = "id"),
            @Mapping(source = "userId", target = "owner")
    })
    CommentResponse from(NewsComment comment);

    NewsComment from(CommentRequest commentRequest);
}
