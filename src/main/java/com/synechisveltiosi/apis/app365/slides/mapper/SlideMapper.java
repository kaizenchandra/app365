package com.synechisveltiosi.apis.app365.slides.mapper;

import com.synechisveltiosi.apis.app365.slides.Slide;
import com.synechisveltiosi.apis.app365.slides.dto.SlideResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SlideMapper {

    SlideMapper INSTANCE = Mappers.getMapper(SlideMapper.class);

    @Mapping(source = "slideId", target = "id")
    SlideResponse from(Slide slide);
}
