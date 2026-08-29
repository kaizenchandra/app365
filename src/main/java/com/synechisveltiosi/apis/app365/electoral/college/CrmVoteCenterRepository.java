package com.synechisveltiosi.apis.app365.electoral.college;

import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.electoral.college.dto.VoteCenterResponse;

import java.io.IOException;

public interface CrmVoteCenterRepository {

    VoteCenterResponse findFirstCollege(Account account, String idCard) throws IOException;
}
