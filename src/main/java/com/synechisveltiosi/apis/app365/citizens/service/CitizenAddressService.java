package com.synechisveltiosi.apis.app365.citizens.service;

import com.synechisveltiosi.apis.app365.citizens.entity.CitizenAddress;

import java.util.Optional;

public interface CitizenAddressService {

    Optional<CitizenAddress> findById(Long id);

    Optional<CitizenAddress> findByCitizenId(Long citizenId);

    CitizenAddress save(CitizenAddress address);

    CitizenAddress patch(CitizenAddress address);
}
