package com.synechisveltiosi.apis.app365.campaign.mapper;

import com.synechisveltiosi.apis.app365.campaign.dto.VolunteerResponse;
import com.synechisveltiosi.apis.app365.campaign.entity.Volunteer;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class VolunteerToVolunteerResponseMapper extends AbstractMapper<Volunteer, VolunteerResponse> {

    @Override
    public VolunteerResponse map(Volunteer volunteer) {
        return VolunteerMapper.INSTANCE.from(volunteer);
    }
}
