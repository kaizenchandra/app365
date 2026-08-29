package com.synechisveltiosi.apis.app365.citizens.service;

import com.synechisveltiosi.apis.app365.citizens.entity.Citizen;

import java.util.Optional;

public interface CitizenService {

    Optional<Citizen> findById(Long id);

    Optional<Citizen> findById(String id);

    Optional<Citizen> findByIdCard(String idCard);
}
