package com.synechisveltiosi.apis.app365.status;

import com.synechisveltiosi.apis.app365.election.ElectionService;
import com.synechisveltiosi.apis.app365.election.dto.ElectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/status",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusController {

    private final ElectionService electionService;

    @Autowired
    public StatusController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @GetMapping(value = "/health", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<ElectionResponse> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
