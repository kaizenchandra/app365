package com.synechisveltiosi.apis.app365.address.repository;

import com.synechisveltiosi.apis.app365.address.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    Optional<Sector> findBySectorId(String sectorId);

    List<Sector> findAllByCityId_CityIdAndSectorIdIsNotNullAndUserDefinedIsFalseOrderByNameAsc(String cityId);
}
