package com.synechisveltiosi.apis.app365.address.mapper;

import com.synechisveltiosi.apis.app365.address.entity.Address;
import com.synechisveltiosi.apis.app365.citizens.entity.CitizenAddress;
import com.synechisveltiosi.apis.app365.common.dto.places.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AddressMapperDecorator implements AddressMapper {

    private final AddressMapper mapper;

    public AddressMapperDecorator(AddressMapper mapper) {
        this.mapper = mapper;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public AddressResponse from(Address address) {
        AddressResponse addressResponse = mapper.from(address);

        if (addressResponse == null) return null;

        // Build line 1
        String home = addressResponse.getHome();
        String apartment = addressResponse.getApartment();
        String street = addressResponse.getStreet();

        List<String> lineParts = Arrays.asList(!StringUtils.isBlank(street) ? street : "",
                StringUtils.join(new String[]{!StringUtils.isBlank(home) ? home : "",
                        !StringUtils.isBlank(apartment) ? apartment : ""}, ""));

        addressResponse.setLine(StringUtils.join(
                lineParts.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList()), ", "));

        // Build full address
        SectorResponse sector = addressResponse.getSector();
        CityResponse city = addressResponse.getCity();
        MunicipalityResponse municipality = addressResponse.getMunicipality();
        StateResponse state = addressResponse.getState();
        CountryResponse country = addressResponse.getCountry();

        List<String> fullAddressParts = Arrays.asList(addressResponse.getLine(),
                sector != null && StringUtils.isNotEmpty(sector.getName()) ? sector.getName() : "",
                city != null && StringUtils.isNotEmpty(city.getName()) ? city.getName() : "",
                municipality != null && StringUtils.isNotEmpty(municipality.getName()) ? municipality.getName() : "",
                state != null && StringUtils.isNotEmpty(state.getName()) ? state.getName() : "",
                country != null && StringUtils.isNotEmpty(country.getName()) ? country.getName() : "");

        addressResponse.setFullAddress(StringUtils.join(
                fullAddressParts.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList()), ", "));

        return addressResponse;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public AddressResponse from(CitizenAddress address) {
        AddressResponse addressResponse = mapper.from(address);

        if (addressResponse == null) return null;

        // Build line 1
        String home = addressResponse.getHome();
        String apartment = addressResponse.getApartment();
        String street = addressResponse.getStreet();

        List<String> lineParts = Arrays.asList(!StringUtils.isBlank(street) ? street : "",
                StringUtils.join(new String[]{!StringUtils.isBlank(home) ? home : "",
                        !StringUtils.isBlank(apartment) ? apartment : ""}, ""));

        addressResponse.setLine(StringUtils.join(
                lineParts.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList()), ", "));

        // Build full address
        SectorResponse sector = addressResponse.getSector();
        CityResponse city = addressResponse.getCity();
        MunicipalityResponse municipality = addressResponse.getMunicipality();
        StateResponse state = addressResponse.getState();
        CountryResponse country = addressResponse.getCountry();

        List<String> fullAddressParts = Arrays.asList(addressResponse.getLine(),
                sector != null && StringUtils.isNotEmpty(sector.getName()) ? sector.getName() : "",
                city != null && StringUtils.isNotEmpty(city.getName()) ? city.getName() : "",
                municipality != null && StringUtils.isNotEmpty(municipality.getName()) ? municipality.getName() : "",
                state != null && StringUtils.isNotEmpty(state.getName()) ? state.getName() : "",
                country != null && StringUtils.isNotEmpty(country.getName()) ? country.getName() : "");

        addressResponse.setFullAddress(StringUtils.join(
                fullAddressParts.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList()), ", "));

        return addressResponse;
    }
}
