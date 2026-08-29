package com.synechisveltiosi.apis.app365.citizens.service;

import com.synechisveltiosi.apis.app365.citizens.entity.Citizen;
import com.synechisveltiosi.apis.app365.citizens.repository.CitizenRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CitizenServiceImpl implements CitizenService {

    private final CitizenRepository citizenRepository;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public Optional<Citizen> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("Citizen id should not be null or 0");

        return citizenRepository.findById(id);
    }

    @Override
    public Optional<Citizen> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("Citizen id should not be null or blank");

        return citizenRepository.findByCitizenId(id);
    }

    @Override
    public Optional<Citizen> findByIdCard(String idCard) {
        if (StringUtils.isBlank(idCard)) throw new BadRequestException("Citizen id card should not be null or blank");

        return citizenRepository.findByIdCard(idCard);
    }
}
