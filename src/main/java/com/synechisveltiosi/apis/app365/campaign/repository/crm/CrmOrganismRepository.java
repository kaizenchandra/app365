package com.synechisveltiosi.apis.app365.campaign.repository.crm;

import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.campaign.Organism;

import java.io.IOException;
import java.util.List;

public interface CrmOrganismRepository {

    List<Organism> findAssociatedOrganism(Account account) throws IOException;

    List<Organism> findSupportSource(Account account) throws IOException;

    List<Organism> findAssociatedOrganismFromSupportSource(Account account, String id) throws IOException;


}
