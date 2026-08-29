package com.synechisveltiosi.apis.app365.candidates.mapper;

import com.synechisveltiosi.apis.app365.candidates.dto.CandidateResponse;
import com.synechisveltiosi.apis.app365.candidates.dto.PoliticalParty;
import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import com.synechisveltiosi.apis.app365.common.dto.DefaultMetaResponse;
import com.synechisveltiosi.apis.app365.users.dto.UserActionMetaResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public abstract class CandidateMapperDecorator implements CandidateMapper {

    private final CandidateMapper mapper;

    public CandidateMapperDecorator(CandidateMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CandidateResponse from(Candidate candidate) {
        CandidateResponse candidateResponse = mapper.from(candidate);
        if (candidateResponse.getParty() != null) {
            candidateResponse.getParty().setPosition(candidate.getCandidateFor());
            return candidateResponse;
        }

        if (!StringUtils.isBlank(candidate.getCandidateFor()))
            candidateResponse.setParty(new PoliticalParty().withPosition(candidate.getCandidateFor()));

        return candidateResponse;
    }

    @Override
    public CandidateResponse from(Long userId, Candidate candidate) {
        CandidateResponse candidateResponse = mapper.from(candidate);

        // Mark user likes
        markUserLikes(userId, candidate, candidateResponse);

        // Mark user share
        markUserShare(userId, candidate, candidateResponse);

        // Map de last comment
        mapLastComment(candidate, candidateResponse);

        return candidateResponse;
    }

    @SuppressWarnings("Duplicates")
    private static void markUserShare(Long userId, Candidate candidate, CandidateResponse candidateResponse) {
        if (!candidate.getShares().isEmpty()) {
            // If the user is found that means he shared this event already, add the share flag
            candidate.getShares().stream()
                    .filter(candidateShare -> Objects.equals(candidateShare.getUserId().getId(), userId))
                    .forEach(candidateShare -> {
                        if (candidateResponse.getMeta() == null)
                            candidateResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
                        candidateResponse.getMeta().getUser().withShared(Boolean.TRUE);
                    });
        }
    }

    @SuppressWarnings("Duplicates")
    private static void markUserLikes(Long userId, Candidate candidate, CandidateResponse candidateResponse) {
        if (!candidate.getLikes().isEmpty()) {
            // If I liked this candidate already, add the like flag
            candidate.getLikes().stream()
                    .filter(candidateLike -> Objects.equals(candidateLike.getUserId().getId(), userId))
                    .forEach(candidateLike -> {
                        if (candidateResponse.getMeta() == null)
                            candidateResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
                        candidateResponse.getMeta().getUser().withLiked(candidateLike.isLiked());
                    });
        }
    }

    @SuppressWarnings("Duplicates")
    private static void mapLastComment(Candidate candidate, CandidateResponse candidateResponse) {
        if (candidate.getLastComment() != null) {
            if (candidateResponse.getMeta() == null)
                candidateResponse.setMeta(new DefaultMetaResponse().withUser(new UserActionMetaResponse()));
            candidateResponse.getMeta().setLastComment(CandidateCommentMapper.INSTANCE.from(candidate.getLastComment()));
        }
    }
}
