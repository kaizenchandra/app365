package com.synechisveltiosi.apis.app365.address.service;

import com.synechisveltiosi.apis.app365.address.entity.Address;

import java.util.Optional;

public interface AddressService {

    Optional<Address> findById(Long id);

    Optional<Address> findByUserId(Long userId);

    Address save(Address address);

    Address patch(Address address);
}
