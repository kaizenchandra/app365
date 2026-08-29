package com.synechisveltiosi.apis.app365.election;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {

    Optional<Election> findFirstByTypeAndActiveIsTrueAndDateGreaterThanEqualOrderByDateAsc(ElectionType type,
                                                                                           LocalDate date);
}
