package com.synechisveltiosi.apis.app365.electoral.roll;

import com.synechisveltiosi.apis.app365.citizens.entity.Citizen;
import com.synechisveltiosi.apis.app365.citizens.exception.CitizenNotFoundException;
import com.synechisveltiosi.apis.app365.citizens.mapper.CitizenMapper;
import com.synechisveltiosi.apis.app365.citizens.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping(value = "/electoralRoll",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ElectoralRollController {

    private final CitizenService citizenService;

    @Autowired
    public ElectoralRollController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getByIdCard(@NotBlank @RequestHeader("X-ID-Card") String idCard) {
        Optional<Citizen> citizenOptional = citizenService.findByIdCard(idCard);
        if (!citizenOptional.isPresent()) throw new CitizenNotFoundException();

        return ResponseEntity.ok(CitizenMapper.INSTANCE.from(citizenOptional.get()));
    }
}
