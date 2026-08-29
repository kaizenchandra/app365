package com.synechisveltiosi.apis.app365.address.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechisveltiosi.apis.app365.address.entity.*;
import com.synechisveltiosi.apis.app365.citizens.entity.CitizenAddress;
import com.synechisveltiosi.apis.app365.common.dto.places.AddressResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

public final class AddressHelper {

    public static Map<String, Object> from(AddressResponse address, ObjectMapper objectMapper)
            throws IOException {

        if (address == null) return null;

        String addressJson = objectMapper.writeValueAsString(address);

        //noinspection unchecked
        return objectMapper.readValue(addressJson, Map.class);
    }

    @SuppressWarnings("Duplicates")
    public static Address from(Map<String, Object> addressMap) {
        Address address = new Address();

        // Set the country
        String countryId = getId(addressMap, "country");
        if (!StringUtils.isBlank(countryId)) {
            Country country = new Country();
            country.setCountryId(countryId);

            address.setCountryId(country);
        }

        // Set the state
        String stateId = getId(addressMap, "state");
        String stateName = getName(addressMap, "state");
        if (!StringUtils.isBlank(stateId)) {
            State state = new State();
            state.setStateId(stateId);

            address.setStateId(state);
        } else if (!StringUtils.isBlank(stateName)) {
            State state = new State();
            state.setName(stateName);

            address.setStateId(state);
        }

        // Set the municipality
        String municipalityId = getId(addressMap, "municipality");
        String municipalityName = getName(addressMap, "municipality");
        if (municipalityId != null) {
            Municipality municipality = new Municipality();
            municipality.setMunicipalityId(municipalityId);

            address.setMunicipalityId(municipality);
        } else if (municipalityName != null) {
            Municipality municipality = new Municipality();
            municipality.setName(municipalityName);

            address.setMunicipalityId(municipality);
        }

        // Set the city
        String cityId = getId(addressMap, "city");
        String cityName = getName(addressMap, "city");
        if (!StringUtils.isBlank(cityId)) {
            City city = new City();
            city.setCityId(cityId);

            address.setCityId(city);
        } else if (!StringUtils.isBlank(cityName)) {
            City city = new City();
            city.setName(cityName);

            address.setCityId(city);
        }

        // Set the sector
        String sectorId = getId(addressMap, "sector");
        String sectorName = getName(addressMap, "sector");
        if (sectorId != null) {
            Sector sector = new Sector();
            sector.setSectorId(sectorId);

            address.setSectorId(sector);
        } else if (sectorName != null) {
            Sector sector = new Sector();
            sector.setName(sectorName);

            address.setSectorId(sector);
        }

        // Set street
        String street = getFlatValue(addressMap, "street");
        if (street != null) {
            address.setStreet(street);
        }

        // Set home
        String home = getFlatValue(addressMap, "home");
        if (home != null) {
            address.setHome(home);
        }

        // Set home
        String apartment = getFlatValue(addressMap, "apartment");
        if (apartment != null) {
            address.setApartment(apartment);
        }

        // Set the zip code
        String zipCode = getFlatValue(addressMap, "zipCode");
        if (zipCode != null) {
            address.setZipCode(zipCode);
        }

        // Set the reference
        String reference = getFlatValue(addressMap, "reference");
        if (reference != null) {
            address.setReference(reference);
        }

        // Set the additional sector
        String additionalSector = getFlatValue(addressMap, "additionalSector");
        if (additionalSector != null) {
            address.setAdditionalSector(additionalSector);
        }

        return address;
    }

    @SuppressWarnings("Duplicates")
    public static CitizenAddress toCitizenAddress(Map<String, Object> addressMap) {
        CitizenAddress address = new CitizenAddress();

        // Set the country
        String countryId = getId(addressMap, "country");
        if (!StringUtils.isBlank(countryId)) {
            Country country = new Country();
            country.setCountryId(countryId);

            address.setCountryId(country);
        }

        // Set the state
        String stateId = getId(addressMap, "state");
        String stateName = getName(addressMap, "state");
        if (!StringUtils.isBlank(stateId)) {
            State state = new State();
            state.setStateId(stateId);

            address.setStateId(state);
        } else if (!StringUtils.isBlank(stateName)) {
            State state = new State();
            state.setName(stateName);

            address.setStateId(state);
        }

        // Set the municipality
        String municipalityId = getId(addressMap, "municipality");
        String municipalityName = getName(addressMap, "municipality");
        if (municipalityId != null) {
            Municipality municipality = new Municipality();
            municipality.setMunicipalityId(municipalityId);

            address.setMunicipalityId(municipality);
        } else if (municipalityName != null) {
            Municipality municipality = new Municipality();
            municipality.setName(municipalityName);

            address.setMunicipalityId(municipality);
        }

        // Set the city
        String cityId = getId(addressMap, "city");
        String cityName = getName(addressMap, "city");
        if (!StringUtils.isBlank(cityId)) {
            City city = new City();
            city.setCityId(cityId);

            address.setCityId(city);
        } else if (!StringUtils.isBlank(cityName)) {
            City city = new City();
            city.setName(cityName);

            address.setCityId(city);
        }

        // Set the sector
        String sectorId = getId(addressMap, "sector");
        String sectorName = getName(addressMap, "sector");
        if (sectorId != null) {
            Sector sector = new Sector();
            sector.setSectorId(sectorId);

            address.setSectorId(sector);
        } else if (sectorName != null) {
            Sector sector = new Sector();
            sector.setName(sectorName);

            address.setSectorId(sector);
        }

        // Set street
        String street = getFlatValue(addressMap, "street");
        if (street != null) {
            address.setStreet(street);
        }

        // Set home
        String home = getFlatValue(addressMap, "home");
        if (home != null) {
            address.setHome(home);
        }

        // Set home
        String apartment = getFlatValue(addressMap, "apartment");
        if (apartment != null) {
            address.setApartment(apartment);
        }

        // Set the zip code
        String zipCode = getFlatValue(addressMap, "zipCode");
        if (zipCode != null) {
            address.setZipCode(zipCode);
        }

        // Set the reference
        String reference = getFlatValue(addressMap, "reference");
        if (reference != null) {
            address.setReference(reference);
        }

        return address;
    }

    private static String getId(Map<String, Object> addressMap, String key) {
        // Try to get the id
        if (addressMap.get(key) != null && (addressMap.get(key) instanceof Map)) {
            //noinspection unchecked
            Map<String, String> object = (Map<String, String>) addressMap.get(key);

            return object.get("id");
        }

        return null;
    }

    private static String getName(Map<String, Object> addressMap, String key) {
        // Try to get the name
        if (addressMap.get(key) != null && (addressMap.get(key) instanceof Map)) {
            //noinspection unchecked
            Map<String, String> object = (Map<String, String>) addressMap.get(key);

            return object.get("name");
        }

        return null;
    }

    private static String getFlatValue(Map<String, Object> addressMap, String key) {
        // Try to get the id
        if (addressMap.get(key) != null && (addressMap.get(key) instanceof String)) {
            return String.valueOf(addressMap.get(key));
        }

        return null;
    }
}
