package com.synechisveltiosi.apis.app365.address.service;

import com.applepolitical.apis.applepolitical365.address.entity.*;
import com.applepolitical.apis.applepolitical365.address.exception.*;
import com.synechisveltiosi.apis.app365.address.entity.*;
import com.synechisveltiosi.apis.app365.address.exception.*;
import com.synechisveltiosi.apis.app365.address.repository.AddressRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CountryService countryService;
    private final StateService stateService;
    private final MunicipalityService municipalityService;
    private final CityService cityService;
    private final SectorService sectorService;

    @Autowired
    public AddressServiceImpl(
            AddressRepository addressRepository, CountryService countryService, StateService stateService,
            MunicipalityService municipalityService, CityService cityService, SectorService sectorService) {

        this.addressRepository = addressRepository;
        this.countryService = countryService;
        this.stateService = stateService;
        this.municipalityService = municipalityService;
        this.cityService = cityService;
        this.sectorService = sectorService;
    }

    @Override
    public Optional<Address> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Address id should not be null or 0");

        return addressRepository.findById(id);
    }

    @Override
    public Optional<Address> findByUserId(Long userId) {
        if (userId == null || userId == 0) throw new BadRequestException("User id should not be null or 0");

        return addressRepository.findByUserId_Id(userId);
    }

    @Transactional
    @Override
    public Address save(Address address) {
        // Validate the address
        if (address == null) throw new BadRequestException("Address should not be null");

        // Validate the user associated with this address
        if (address.getUserId() == null || address.getUserId().getId() == null || address.getUserId().getId() == 0)
            throw new BadRequestException("The user associate with this address should not be null.");

        // Validate the country
        if (address.getCountryId() == null || StringUtils.isBlank(address.getCountryId().getCountryId()))
            throw new BadRequestException("Country id should not be null or blank");

        // Validate the province
        if (address.getStateId() == null || StringUtils.isBlank(address.getStateId().getStateId()))
            throw new BadRequestException("State id should not be null or blank");

        // Validate the city
        if (address.getMunicipalityId() == null || StringUtils.isBlank(address.getMunicipalityId().getMunicipalityId()))
            throw new BadRequestException("Municipality id should not be null or blank");

        // Validate the address street
        if (StringUtils.isBlank(address.getStreet()))
            throw new BadRequestException("Street should not be null or blank");

        // Set the country
        address.setCountryId(countryService.findById(address.getCountryId().getCountryId())
                .orElseThrow(CountryNotFoundException::new));

        // Set new state
        State state = address.getStateId();
        processState(address, state);

        // Set new municipality
        Municipality municipality = address.getMunicipalityId();
        processMunicipality(address, municipality);

        // Set new city
        City city = address.getCityId();
        processCity(address, city);

        // Set new sectors
        Sector sector = address.getSectorId();
        processSector(address, sector);

        return addressRepository.save(address);
    }

    @Transactional
    @Override
    public Address patch(Address address) {
        // Validate the address
        if (address == null) throw new BadRequestException("Address should not be null");

        // Validate the user associated with this address
        if (address.getUserId() == null || address.getUserId().getId() == null || address.getUserId().getId() == 0)
            throw new BadRequestException("The user associate with this address should not be null.");

        // Try to find address for the user
        Optional<Address> addressOptional = findByUserId(address.getUserId().getId());
        if (!addressOptional.isPresent()) {
            return this.save(address);
        }

        // Update the address
        Address oldAddress = addressOptional.get();

        // Set new country
        if (address.getCountryId() != null) {
            oldAddress.setCountryId(
                    countryService.findById(address.getCountryId().getCountryId())
                            .orElseThrow(CountryNotFoundException::new));
        }

        // Set new state
        State state = address.getStateId();
        processState(oldAddress, state);

        // Set new municipality
        Municipality municipality = address.getMunicipalityId();
        processMunicipality(oldAddress, municipality);

        // Set new city
        City city = address.getCityId();
        processCity(oldAddress, city);

        // Set new sectors
        Sector sector = address.getSectorId();
        processSector(oldAddress, sector);

        // Set street
        if (address.getStreet() != null) {
            oldAddress.setStreet(address.getStreet());
        }

        // Set home
        if (address.getHome() != null) {
            oldAddress.setHome(address.getHome());
        }

        // Set apartment
        if (address.getApartment() != null) {
            oldAddress.setApartment(address.getApartment());
        }

        // Set zip code
        if (address.getZipCode() != null) {
            oldAddress.setZipCode(address.getZipCode());
        }

        // Set reference
        if (address.getReference() != null) {
            oldAddress.setReference(address.getReference());
        }

        // Set reference
        if (address.getAdditionalSector() != null) {
            oldAddress.setAdditionalSector(address.getAdditionalSector());
        }

        return addressRepository.save(oldAddress);
    }

    @SuppressWarnings("Duplicates")
    private void processState(Address address, State state) {
        if (state != null) {
            if (!StringUtils.isBlank(state.getStateId())) {
                address.setStateId(
                        stateService.findById(state.getStateId())
                                .orElseThrow(StateNotFoundException::new));
            } else if (!StringUtils.isBlank(state.getName())) {
                state.setCountryId(address.getCountryId());
                state.setUserDefined(Boolean.TRUE);

                // Save the new state
                State newState = stateService.save(state);
                address.setStateId(newState);
            } else {
                throw new BadRequestException("State is required for the address.");
            }
        } else {
            throw new BadRequestException("State is required for the address.");
        }
    }

    @SuppressWarnings("Duplicates")
    private void processMunicipality(Address address, Municipality municipality) {
        if (municipality != null) {
            if (!StringUtils.isBlank(municipality.getMunicipalityId())) {
                address.setMunicipalityId(
                        municipalityService.findById(municipality.getMunicipalityId())
                                .orElseThrow(MunicipalityNotFoundException::new));
            } else if (!StringUtils.isBlank(municipality.getName())) {
                if (address.getStateId() != null)
                    municipality.setStateId(address.getStateId());

                // Save the new municipality
                municipality.setUserDefined(Boolean.TRUE);
                Municipality newMunicipality = municipalityService.save(municipality);
                address.setMunicipalityId(newMunicipality);
            } else if (StringUtils.isBlank(municipality.getMunicipalityId())
                    || StringUtils.isBlank(municipality.getName())) {
                address.setMunicipalityId(null);
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void processCity(Address address, City city) {
        if (city != null) {
            if (!StringUtils.isBlank(city.getCityId())) {
                address.setCityId(
                        cityService.findById(city.getCityId())
                                .orElseThrow(CityNotFoundException::new));
            } else if (!StringUtils.isBlank(city.getName())) {
                if (address.getMunicipalityId() != null)
                    city.setMunicipalityId(address.getMunicipalityId());

                // Save the new city
                city.setUserDefined(Boolean.TRUE);
                City newCity = cityService.save(city);
                address.setCityId(newCity);
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void processSector(Address address, Sector sector) {
        if (sector != null) {
            if (!StringUtils.isBlank(sector.getSectorId())) {
                address.setSectorId(
                        sectorService.findById(sector.getSectorId())
                                .orElseThrow(SectorNotFoundException::new));
            } else if (!StringUtils.isBlank(sector.getName())) {
                if (address.getCityId() != null)
                    sector.setCityId(address.getCityId());

                // Save the new sector
                sector.setUserDefined(Boolean.TRUE);
                Sector newSector = sectorService.save(sector);
                address.setSectorId(newSector);
            } else if (StringUtils.isBlank(sector.getSectorId())
                    || StringUtils.isBlank(sector.getName())) {
                address.setSectorId(null);
            }
        }
    }
}
