package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.Municipality;
import com.synechisveltiosi.apis.app365.common.dto.places.MunicipalityResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityToMunicipalityResponseMapper extends AbstractMapper<Municipality, MunicipalityResponse> {

    @Override
    public MunicipalityResponse map(Municipality municipality) {
        return MunicipalityMapper.INSTANCE.from(municipality);
    }
}
