package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.State;
import com.synechisveltiosi.apis.app365.address.repository.StateRepository;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    @Autowired
    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public List<State> findAll(String countryId) {
        if (StringUtils.isBlank(countryId)) throw new BadRequestException("Country id should not be null or blank");

        return stateRepository.findAllByCountryId_CountryIdAndStateIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(countryId);
    }


    @Override
    public List<State> findAllIso2(String countryIso2) {
        if (StringUtils.isBlank(countryIso2)) throw new BadRequestException("Country iso2 should not be null or blank");

        return stateRepository.findAllByCountryId_Iso2AndStateIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(countryIso2);
    }

    @Override
    public Optional<State> findById(Long id) {
        if (id == null || id == 0) throw new BadRequestException("State id should not be null or 0");

        return stateRepository.findById(id);
    }

    @Override
    public Optional<State> findById(String id) {
        if (StringUtils.isBlank(id)) throw new BadRequestException("State id should not be null or blank");

        return stateRepository.findByStateId(id);
    }

    @Override
    public State save(State state) {
        return stateRepository.save(state);
    }
}
