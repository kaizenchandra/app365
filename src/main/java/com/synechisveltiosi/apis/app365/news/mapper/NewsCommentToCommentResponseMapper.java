package com.synechisveltiosi.apis.app365.news.mapper;

import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.news.entity.NewsComment;
import org.springframework.stereotype.Component;

@Component
public class NewsCommentToCommentResponseMapper extends AbstractMapper<NewsComment, CommentResponse> {

    @Override
    public CommentResponse map(NewsComment newsComment) {
        return NewsCommentMapper.INSTANCE.from(newsComment);
    }
}
