package com.synechisveltiosi.apis.app365.campaign.repository.crm;

import com.synechisveltiosi.apis.app365.accounts.entity.Account;
import com.synechisveltiosi.apis.app365.campaign.dto.*;
import com.synechisveltiosi.apis.app365.campaign.entity.TeamLevel;
import com.synechisveltiosi.apis.app365.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CrmTeamMemberRepository {

    Page<TeamMemberResponse> findAllTeamMember(Account account, User user, Pageable pageable) throws IOException;

    List<TeamLevel> findLevelMembers(Account account, String idCard) throws IOException;

    CbaUser findUser(Account account, String idCard) throws IOException;

    void addHeader(Account account, CbaHeaderRequest request) throws IOException;

    void save(Account account, MilitantRequest militantRequest) throws IOException;

    void update(Account account, String memberId, TeamMemberRequest memberRequest) throws IOException;

    void patchAddress(Account account, String memberId, Map<String, Object> addressPatch) throws IOException;

    void verifyTeamMembersEmailAddress(Account account, String email, String idCard) throws IOException;

    void verifyTeamMembersPhoneNumbers(Account account, String countryID, String type, String phone, String idCard) throws IOException;
}
