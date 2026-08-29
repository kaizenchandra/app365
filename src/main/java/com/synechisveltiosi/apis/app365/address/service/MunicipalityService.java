package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.Municipality;

import java.util.List;
import java.util.Optional;

public interface MunicipalityService {

    List<Municipality> findAll(String stateId);

    Optional<Municipality> findById(Long id);

    Optional<Municipality> findById(String id);

    Municipality save(Municipality municipality);
}
