package com.synechisveltiosi.apis.app365.address.repository;

import com.synechisveltiosi.apis.app365.address.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    Optional<State> findByStateId(String stateId);

    List<State> findAllByCountryId_CountryIdAndStateIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(String countryId);

    List<State> findAllByCountryId_Iso2AndStateIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(String iso2);
}
