package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.Municipality;
import com.synechisveltiosi.apis.app365.address.repository.MunicipalityRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MunicipalityServiceImpl implements MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    @Autowired
    public MunicipalityServiceImpl(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
    }

    @Override
    public List<Municipality> findAll(String stateId) {
        if (StringUtils.isBlank(stateId)) throw new BadRequestException("State id should not be null or blank");

        return municipalityRepository.findAllByStateId_StateIdAndMunicipalityIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(stateId);
    }

    @Override
    public Optional<Municipality> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Municipality id should not be null or 0");

        return municipalityRepository.findById(id);
    }

    @Override
    public Optional<Municipality> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Municipality id should not be null or blank");

        return municipalityRepository.findByMunicipalityId(id);
    }

    @Override
    public Municipality save(Municipality municipality) {
        return municipalityRepository.save(municipality);
    }
}
