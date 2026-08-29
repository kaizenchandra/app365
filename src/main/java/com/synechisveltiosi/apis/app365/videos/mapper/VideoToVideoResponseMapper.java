package com.synechisveltiosi.apis.app365.videos.mapper;

import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.videos.dto.VideoResponse;
import com.synechisveltiosi.apis.app365.videos.entity.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoToVideoResponseMapper extends AbstractMapper<Video, VideoResponse> {

    @Override
    public VideoResponse map(Video video) {
        return VideoMapper.INSTANCE.from(video);
    }
}
