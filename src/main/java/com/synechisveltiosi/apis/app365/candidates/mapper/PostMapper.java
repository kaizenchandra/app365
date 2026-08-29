package com.synechisveltiosi.apis.app365.candidates.mapper;

import com.synechisveltiosi.apis.app365.candidates.dto.PostResponse;
import com.synechisveltiosi.apis.app365.candidates.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mappings({
            @Mapping(source = "postId", target = "id"),
            @Mapping(source = "candidateId", target = "owner"),
            @Mapping(source = "externalCreatedAt", target = "createdAt"),
            @Mapping(source = "createdAt", target = "reportedAt")
    })
    PostResponse from(Post post);
}
