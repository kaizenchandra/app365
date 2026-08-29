package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.Country;
import com.synechisveltiosi.apis.app365.address.repository.CountryRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAllByCountryIdIsNotNullOrderByNameAsc();
    }

    @Override
    public Optional<Country> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Country id should not be null or 0");

        return countryRepository.findById(id);
    }

    @Override
    public Optional<Country> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Country id should not be null or blank");

        return countryRepository.findByCountryId(id);
    }

    @Override
    public Optional<Country> findByIso2(String iso2) {
        if (StringUtils.isBlank(iso2) || iso2.length() != 2)
            throw new BadRequestException("Country iso2 should not be null, blank or more / less than 2 characters");

        return countryRepository.findByIso2(iso2);
    }
}
