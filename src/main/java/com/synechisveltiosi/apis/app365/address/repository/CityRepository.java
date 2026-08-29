package com.synechisveltiosi.apis.app365.address.repository;

import com.synechisveltiosi.apis.app365.address.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByCityId(String cityId);

    List<City> findAllByMunicipalityId_MunicipalityIdAndCityIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(String municipalityId);
}
