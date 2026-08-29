package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.City;

import java.util.List;
import java.util.Optional;

public interface CityService {

    List<City> findAll(String municipalityId);

    Optional<City> findById(Long id);

    Optional<City> findById(String id);

    City save(City city);
}
