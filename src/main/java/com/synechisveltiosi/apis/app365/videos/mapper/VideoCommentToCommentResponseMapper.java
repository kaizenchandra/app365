package com.synechisveltiosi.apis.app365.videos.mapper;

import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.videos.entity.VideoComment;
import org.springframework.stereotype.Component;

@Component
public class VideoCommentToCommentResponseMapper extends AbstractMapper<VideoComment, CommentResponse> {

    @Override
    public CommentResponse map(VideoComment videoComment) {
        return VideoCommentMapper.INSTANCE.from(videoComment);
    }
}
