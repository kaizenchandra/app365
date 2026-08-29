package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.City;
import com.synechisveltiosi.apis.app365.address.repository.CityRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> findAll(String municipalityId) {
        if (StringUtils.isBlank(municipalityId))
            throw new BadRequestException("Municipality id should not be null or blank");

        return cityRepository.findAllByMunicipalityId_MunicipalityIdAndCityIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(municipalityId);
    }

    @Override
    public Optional<City> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("City id should not be null or 0");

        return cityRepository.findById(id);
    }

    @Override
    public Optional<City> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("City id should not be null or blank");

        return cityRepository.findByCityId(id);
    }

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }
}
