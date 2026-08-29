package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.Address;
import com.synechisveltiosi.apis.app365.citizens.entity.CitizenAddress;
import com.synechisveltiosi.apis.app365.common.dto.places.AddressResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {CountryMapper.class, StateMapper.class, MunicipalityMapper.class, CityMapper.class, SectorMapper.class})
@DecoratedWith(AddressMapperDecorator.class)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mappings({
            @Mapping(source = "countryId", target = "country"),
            @Mapping(source = "stateId", target = "state"),
            @Mapping(source = "municipalityId", target = "municipality"),
            @Mapping(source = "cityId", target = "city"),
            @Mapping(source = "sectorId", target = "sector")
    })
    AddressResponse from(Address address);

    @Mappings({
            @Mapping(source = "countryId", target = "country"),
            @Mapping(source = "stateId", target = "state"),
            @Mapping(source = "municipalityId", target = "municipality"),
            @Mapping(source = "cityId", target = "city"),
            @Mapping(source = "sectorId", target = "sector")
    })
    AddressResponse from(CitizenAddress address);
}
