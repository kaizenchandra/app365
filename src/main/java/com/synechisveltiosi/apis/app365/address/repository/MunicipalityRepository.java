package com.synechisveltiosi.apis.app365.address.repository;

import com.synechisveltiosi.apis.app365.address.entity.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

    Optional<Municipality> findByMunicipalityId(String municipalityId);

    List<Municipality> findAllByStateId_StateIdAndMunicipalityIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(String stateId);
}
