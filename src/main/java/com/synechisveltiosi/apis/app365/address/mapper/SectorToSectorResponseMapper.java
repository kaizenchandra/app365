package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.Sector;
import com.synechisveltiosi.apis.app365.common.dto.places.SectorResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import org.springframework.stereotype.Component;

@Component
public class SectorToSectorResponseMapper extends AbstractMapper<Sector, SectorResponse> {

    @Override
    public SectorResponse map(Sector sector) {
        return SectorMapper.INSTANCE.from(sector);
    }
}
