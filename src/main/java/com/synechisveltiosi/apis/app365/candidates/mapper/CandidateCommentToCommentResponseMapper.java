package com.synechisveltiosi.apis.app365.candidates.mapper;

import com.synechisveltiosi.apis.app365.candidates.entity.CandidateComment;
import com.synechisveltiosi.apis.app365.common.dto.comments.CommentResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class CandidateCommentToCommentResponseMapper extends AbstractMapper<CandidateComment, CommentResponse> {

    @Override
    public CommentResponse map(CandidateComment candidateComment) {
        return CandidateCommentMapper.INSTANCE.from(candidateComment);
    }
}
