package com.synechisveltiosi.apis.app365.actions.mapper;

import com.synechisveltiosi.apis.app365.actions.entity.Medal;
import com.synechisveltiosi.apis.app365.actions.dto.MedalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedalMapper {

    MedalMapper INSTANCE = Mappers.getMapper(MedalMapper.class);

    @Mappings({
            @Mapping(source = "medalId", target = "id"),
            @Mapping(source = "image", target = "picture")
    })
    MedalResponse from(Medal medal);
}
