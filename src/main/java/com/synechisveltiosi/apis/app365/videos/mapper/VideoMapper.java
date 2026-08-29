package com.synechisveltiosi.apis.app365.videos.mapper;

import com.synechisveltiosi.apis.app365.videos.dto.VideoResponse;
import com.synechisveltiosi.apis.app365.videos.entity.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VideoMapper {

    VideoMapper INSTANCE = Mappers.getMapper(VideoMapper.class);

    @Mappings({
            @Mapping(source = "videoId", target = "id"),
            @Mapping(source = "videoSummary", target = "summary"),
            @Mapping(source = "userId", target = "owner")
    })
    VideoResponse from(Video video);
}
