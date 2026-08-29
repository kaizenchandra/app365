package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.State;

import java.util.List;
import java.util.Optional;

public interface StateService {

    List<State> findAll(String countryId);

    List<State> findAllIso2(String countryIso2);

    Optional<State> findById(Long id);

    Optional<State> findById(String id);

    State save(State state);
}
