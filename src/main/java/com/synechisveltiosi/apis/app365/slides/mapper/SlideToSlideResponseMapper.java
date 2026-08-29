package com.synechisveltiosi.apis.app365.slides.mapper;

import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.slides.Slide;
import com.synechisveltiosi.apis.app365.slides.dto.SlideResponse;
import org.springframework.stereotype.Component;

@Component
public class SlideToSlideResponseMapper extends AbstractMapper<Slide, SlideResponse> {

    @Override
    public SlideResponse map(Slide slide) {
        return SlideMapper.INSTANCE.from(slide);
    }
}
