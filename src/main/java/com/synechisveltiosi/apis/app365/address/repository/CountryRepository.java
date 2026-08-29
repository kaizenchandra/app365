package com.synechisveltiosi.apis.app365.address.repository;

import com.synechisveltiosi.apis.app365.address.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    List<Country> findAllByCountryIdIsNotNullOrderByNameAsc();

    Optional<Country> findByCountryId(String countryId);

    Optional<Country> findByIso2(String iso2);
}
