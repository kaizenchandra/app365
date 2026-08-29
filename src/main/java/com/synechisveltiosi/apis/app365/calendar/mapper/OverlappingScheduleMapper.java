package com.synechisveltiosi.apis.app365.calendar.mapper;

import com.synechisveltiosi.apis.app365.calendar.dto.OverlappingScheduleResponse;
import com.synechisveltiosi.apis.app365.common.util.date.OverlappingSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OverlappingScheduleMapper {

    OverlappingScheduleMapper INSTANCE = Mappers.getMapper(OverlappingScheduleMapper.class);

    OverlappingScheduleResponse from(OverlappingSchedule overlappingSchedule);
}
