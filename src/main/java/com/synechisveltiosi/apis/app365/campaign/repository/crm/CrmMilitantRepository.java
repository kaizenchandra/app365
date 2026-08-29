package com.synechisveltiosi.apis.app365.campaign.repository.crm;

import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.campaign.dto.Militant;
import com.synechisveltiosi.apis.app365.campaign.dto.MilitantRequest;
import com.synechisveltiosi.apis.app365.common.dto.id.IdCardRequest;

import java.io.IOException;

public interface CrmMilitantRepository {

    Militant findMilitant(Account account, String idCard, Militant.SearchType searchType) throws IOException;

    void addUserAsMilitant(Account account, IdCardRequest idCardRequest) throws IOException;

    void addMilitant(Account account, MilitantRequest militantRequest) throws IOException;

    void updateMilitant(Account account, MilitantRequest militantRequest) throws IOException;

    void verifyMilitantsEmailAddress(Account account, String email, String idCard) throws IOException;

    void verifyMilitantsPhoneNumbers(Account account, String countryID, String type, String phone, String idCard) throws IOException;
}
