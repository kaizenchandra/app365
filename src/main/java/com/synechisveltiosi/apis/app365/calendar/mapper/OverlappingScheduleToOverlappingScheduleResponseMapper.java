package com.synechisveltiosi.apis.app365.calendar.mapper;

import com.synechisveltiosi.apis.app365.calendar.dto.OverlappingScheduleResponse;
import com.synechisveltiosi.apis.app365.common.util.date.OverlappingSchedule;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class OverlappingScheduleToOverlappingScheduleResponseMapper
        extends AbstractMapper<OverlappingSchedule, OverlappingScheduleResponse> {

    @Override
    public OverlappingScheduleResponse map(OverlappingSchedule overlappingSchedule) {
        return OverlappingScheduleMapper.INSTANCE.from(overlappingSchedule);
    }
}
