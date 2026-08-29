package com.synechisveltiosi.apis.app365.citizens.repository;

import com.synechisveltiosi.apis.app365.citizens.entity.CitizenAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenAddressRepository extends JpaRepository<CitizenAddress, Long> {

    Optional<CitizenAddress> findByCitizenId_Id(Long citizenId);
}
