package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.Country;
import com.synechisveltiosi.apis.app365.common.dto.places.CountryResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class CountryToCountryResponseMapper extends AbstractMapper<Country, CountryResponse> {

    @Override
    public CountryResponse map(Country country) {
        return CountryMapper.INSTANCE.from(country);
    }
}
