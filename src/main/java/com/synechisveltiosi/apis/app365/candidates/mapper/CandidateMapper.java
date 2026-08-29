package com.synechisveltiosi.apis.app365.candidates.mapper;

import com.synechisveltiosi.apis.app365.candidates.dto.CandidateResponse;
import com.synechisveltiosi.apis.app365.candidates.entity.Candidate;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
@DecoratedWith(CandidateMapperDecorator.class)
public interface CandidateMapper {

    CandidateMapper INSTANCE = Mappers.getMapper(CandidateMapper.class);

    @Mappings({
            @Mapping(source = "candidateId", target = "id"),
            @Mapping(source = "candidateSummary", target = "summary"),
            @Mapping(source = "biographyDescription", target = "bio.description")
    })
    CandidateResponse from(Candidate candidate);

    @InheritConfiguration
    CandidateResponse from(Long userId, Candidate candidate);
}
