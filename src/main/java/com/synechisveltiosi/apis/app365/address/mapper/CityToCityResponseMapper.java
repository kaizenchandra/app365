package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.City;
import com.synechisveltiosi.apis.app365.common.dto.places.CityResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class CityToCityResponseMapper extends AbstractMapper<City, CityResponse> {

    @Override
    public CityResponse map(City city) {
        return CityMapper.INSTANCE.from(city);
    }
}
