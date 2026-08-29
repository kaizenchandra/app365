package com.synechisveltiosi.apis.app365.candidates.mapper;

import com.synechisveltiosi.apis.app365.candidates.dto.PostResponse;
import com.synechisveltiosi.apis.app365.candidates.entity.Post;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class PostToPostResponseMapper extends AbstractMapper<Post, PostResponse> {

    @Override
    public PostResponse map(Post post) {
        return PostMapper.INSTANCE.from(post);
    }
}
