package com.synechisveltiosi.apis.app365.election;

import com.synechisveltiosi.apis.app365.common.util.date.DateConverter;
import com.synechisveltiosi.apis.app365.election.dto.ElectionResponse;
import com.synechisveltiosi.apis.app365.election.mapper.ElectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/elections",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ElectionController {

    private final ElectionService electionService;

    @Autowired
    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @GetMapping(value = "/{type}/{date}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ElectionResponse> getActiveElection(
            @NotBlank @PathVariable ElectionType type,
            @NotBlank @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable Date date) {

        Optional<Election> electionOptional = electionService.findFirstActiveElectionByTypeAndDate(type,
                DateConverter.fromDate(date).toLocalDate());

        if (!electionOptional.isPresent()) throw new ElectionNotFoundException();

        return ResponseEntity.ok(ElectionMapper.INSTANCE.from(electionOptional.get()));
    }
}
