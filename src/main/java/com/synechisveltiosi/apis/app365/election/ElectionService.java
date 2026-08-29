package com.synechisveltiosi.apis.app365.election;

import java.time.LocalDate;
import java.util.Optional;

public interface ElectionService {

    Optional<Election> findFirstActiveElectionByTypeAndDate(ElectionType type, LocalDate date);
}
