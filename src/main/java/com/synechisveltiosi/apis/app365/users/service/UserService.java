package com.synechisveltiosi.apis.app365.users.service;

import com.synechisveltiosi.apis.app365.common.SocialNetworkProvider;
import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.dto.SocialTokenRequest;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.ConflictException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.UnsupportedChannelException;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerification;
import com.synechisveltiosi.apis.app365.common.util.UserSocialToken;
import com.synechisveltiosi.apis.app365.users.entity.User;
import cz.jirutka.rsql.parser.RSQLParserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    Page<User> findAll(String query, Pageable pageable) throws RSQLParserException;

    Optional<User> findById(Long id);

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);

    User save(User user) throws IOException, ConflictException, UnsupportedChannelException;

    User update(User user);

    User patch(Long id, Map<String, Object> userMap);

    User saveSettings(Long id, Map<String, Object> settings);

    User patchSettings(Long id, Map<String, Object> settings);

    List<UserSocialToken> saveSocialToken(Long userId, UserSocialToken socialToken);

    List<UserSocialToken> updateSocialToken(Long userId, UserSocialToken socialToken);

    List<UserSocialToken> deleteSocialToken(Long userId, SocialNetworkProvider provider);

    void delete(String id);

    AccessToken createUserFromSocialNetwork(SocialTokenRequest socialToken) throws IOException, ConflictException, UnsupportedChannelException;

    void verifyPhoneNumber(String id, PhoneVerification phoneVerification) throws Exception;

    void resendPhoneVerificationCode(String id) throws Exception;

    String resendPhoneVerificationCode(PhoneVerification phoneVerification) throws Exception;
}
