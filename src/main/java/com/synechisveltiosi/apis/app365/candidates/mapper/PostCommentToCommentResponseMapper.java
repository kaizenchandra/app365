package com.synechisveltiosi.apis.app365.candidates.mapper;

import com.synechisveltiosi.apis.app365.candidates.entity.PostComment;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class PostCommentToCommentResponseMapper extends AbstractMapper<PostComment, CommentResponse> {

    @Override
    public CommentResponse map(PostComment postComment) {
        return PostCommentMapper.INSTANCE.from(postComment);
    }
}
