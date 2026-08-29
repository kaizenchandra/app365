package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.Sector;

import java.util.List;
import java.util.Optional;

public interface SectorService {

    List<Sector> findAll(String cityId);

    Optional<Sector> findById(Long id);

    Optional<Sector> findById(String id);

    Sector save(Sector sector);
}
