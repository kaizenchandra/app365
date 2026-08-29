package com.synechisveltiosi.apis.app365.candidates.mapper;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateComment;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentRequest;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CandidateCommentMapper {

    CandidateCommentMapper INSTANCE = Mappers.getMapper(CandidateCommentMapper.class);

    @Mappings({
            @Mapping(source = "commentId", target = "id"),
            @Mapping(source = "userId", target = "owner")
    })
    CommentResponse from(CandidateComment comment);

    CandidateComment from(CommentRequest commentRequest);
}
