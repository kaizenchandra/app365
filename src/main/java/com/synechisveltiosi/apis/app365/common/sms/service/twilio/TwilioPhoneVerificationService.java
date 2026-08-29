package com.synechisveltiosi.apis.app365.common.sms.service.twilio;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerification;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerificationException;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerificationService;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.authy.AuthyApiClient;
import com.authy.AuthyException;
import com.authy.api.Params;
import com.authy.api.Verification;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("twilioPhoneVerificationService")
public class TwilioPhoneVerificationService implements PhoneVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioPhoneVerificationService.class);

    private AuthyApiClient authyApiClient;

    @Autowired
    public TwilioPhoneVerificationService(AuthyApiClient authyApiClient) {
        this.authyApiClient = authyApiClient;
    }

    @Override
    public void sendVerificationCode(User user, PhoneVerification phoneVerification) throws Exception {
        if (phoneVerification == null || StringUtils.isEmpty(phoneVerification.getPhoneCountryCode())
                || StringUtils.isEmpty(phoneVerification.getPhone()))
            throw new BadRequestException("Invalid phone number or country phone code.");

        Params params = new Params();
        params.setAttribute("code_length", "6");

        try {
            Verification verification = authyApiClient
                    .getPhoneVerification()
                    .start(phoneVerification.getPhone(), phoneVerification.getPhoneCountryCode(),
                            phoneVerification.getChannel().name().toLowerCase(), params);

            if (!verification.isOk()) {
                logAndThrow("Error requesting phone verification. " +
                        verification.getMessage());
            }
        } catch (AuthyException ex) {
            throw new Exception(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    public void verify(User user, PhoneVerification phoneVerification) throws Exception {
        if (phoneVerification == null || StringUtils.isEmpty(phoneVerification.getPhoneCountryCode())
                || StringUtils.isEmpty(phoneVerification.getPhone()))
            throw new BadRequestException("Invalid phone number or country phone code or verification code.");

        try {
            Verification verification = authyApiClient
                    .getPhoneVerification()
                    .check(phoneVerification.getPhone(), phoneVerification.getPhoneCountryCode(),
                            phoneVerification.getCode());

            if (!verification.isOk()) {
                logAndThrow("Error verifying token. " + verification.getMessage());
            }
        } catch (AuthyException ex) {
            throw new Exception(ex.getMessage(), ex.getCause());
        }
    }

    private void logAndThrow(String message) {
        logger.warn(message);
        throw new PhoneVerificationException(message);
    }
}
