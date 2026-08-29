package com.synechisveltiosi.apis.app365.address.repository;

import com.synechisveltiosi.apis.app365.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUserId_Id(Long userId);
}
