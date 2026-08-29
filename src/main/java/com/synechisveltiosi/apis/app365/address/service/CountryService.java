package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    List<Country> findAll();

    Optional<Country> findById(Long id);

    Optional<Country> findById(String id);

    Optional<Country> findByIso2(String iso2);
}
