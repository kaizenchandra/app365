package com.synechisveltiosi.apis.app365.citizens.repository;

import com.synechisveltiosi.apis.app365.citizens.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    Optional<Citizen> findByCitizenId(String citizenId);

    Optional<Citizen> findByIdCard(String idCard);
}
