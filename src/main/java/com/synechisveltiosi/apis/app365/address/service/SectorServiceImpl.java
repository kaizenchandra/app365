package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.Sector;
import com.synechisveltiosi.apis.app365.address.repository.SectorRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;

    @Autowired
    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<Sector> findAll(String cityId) {
        if (StringUtils.isBlank(cityId)) throw new BadRequestException("City id should not be null or blank");

        return sectorRepository.findAllByCityId_CityIdAndSectorIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(cityId);
    }

    @Override
    public Optional<Sector> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Sector id should not be null or 0");

        return sectorRepository.findById(id);
    }

    @Override
    public Optional<Sector> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Sector id should not be null or blank");

        return sectorRepository.findBySectorId(id);
    }

    @Override
    public Sector save(Sector sector) {
        return sectorRepository.save(sector);
    }
}
