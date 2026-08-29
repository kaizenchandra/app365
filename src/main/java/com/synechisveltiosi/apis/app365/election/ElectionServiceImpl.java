package com.synechisveltiosi.apis.app365.election;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Clivens Petit <clivens.petit@centallylabs.com>
 */
@Service
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    public ElectionServiceImpl(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    @Override
    public Optional<Election> findFirstActiveElectionByTypeAndDate(ElectionType type, LocalDate date) {
        if (type == null || date == null) throw new BadRequestException();

        return electionRepository.findFirstByTypeAndActiveIsTrueAndDateGreaterThanEqualOrderByDateAsc(type, date);
    }
}
