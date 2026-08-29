package com.synechisveltiosi.apis.app365.electoral.college;

import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.accounts.exception.AccountNotFoundException;
import com.synechisveltiosi.apis.app365.accounts.service.AccountService;
import com.synechisveltiosi.apis.app365.common.auth.SessionUtils;
import com.synechisveltiosi.apis.app365.common.http.request.HttpHeader;
import com.synechisveltiosi.apis.app365.config.AppConfig;
import com.synechisveltiosi.apis.app365.electoral.college.dto.VoteCenterResponse;
import com.synechisveltiosi.apis.app365.electoral.college.mapper.VoteCenterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/electoralCollege",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ElectoralCollegeController {

    private final AccountService accountService;
    private final VoteCenterService voteCenterService;
    private final CrmVoteCenterRepository crmVoteCenterRepository;
    private final AppConfig appConfig;
    private final HttpHeader httpHeader;

    @Autowired
    public ElectoralCollegeController(AccountService accountService, VoteCenterService voteCenterService,
                                      CrmVoteCenterRepository crmVoteCenterRepository, AppConfig appConfig,
                                      HttpHeader httpHeader) {

        this.accountService = accountService;
        this.voteCenterService = voteCenterService;
        this.crmVoteCenterRepository = crmVoteCenterRepository;
        this.appConfig = appConfig;
        this.httpHeader = httpHeader;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> get() throws IOException {
        VoteCenterResponse voteCenterResponse;

        // Find account
        Optional<Account> accountOptional = accountService.findBySubdomain(httpHeader.getTenantId());
        Account account = accountOptional.orElseThrow(AccountNotFoundException::new);
        if (account.getConfiguration().isEnableCrmUse()) {
            voteCenterResponse = crmVoteCenterRepository.findFirstCollege(account,
                    SessionUtils.getLoggedUser().getIdCard());
        } else {
            voteCenterResponse = VoteCenterMapper.INSTANCE.from(
                    voteCenterService.findFirst().orElseThrow(VoteCenterNotFoundException::new));
        }

        return ResponseEntity.ok(voteCenterResponse);
    }
}
