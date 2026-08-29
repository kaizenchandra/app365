package com.synechisveltiosi.apis.app365.common.sms.service.textmagic;

import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerification;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerificationException;
import com.synechisveltiosi.apis.app365.common.sms.PhoneVerificationService;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.textmagic.sdk.RestClient;
import com.textmagic.sdk.RestException;
import com.textmagic.sdk.resource.instance.TMNewMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("textMagicSmsService")
public class TextMagicSmsService implements PhoneVerificationService {

    private static final Logger logger = LoggerFactory.getLogger(TextMagicSmsService.class);

    private final RestClient restClient;
    private final String phoneNumber;
    private final String messageEn;
    private final String messageEs;

    @Autowired
    public TextMagicSmsService(RestClient restClient,
                               @Value("${app365.textmagic.phone-number}") String phoneNumber,
                               @Value("${app365.messages.verification-message-en}") String messageEn,
                               @Value("${app365.messages.verification-message-es}") String messageEs) {

        this.restClient = restClient;
        this.phoneNumber = phoneNumber;
        this.messageEn = messageEn;
        this.messageEs = messageEs;
    }

    @Override
    public void sendVerificationCode(User user, PhoneVerification phoneVerification) throws Exception {
        if (phoneVerification == null || StringUtils.isEmpty(phoneVerification.getPhoneCountryCode())
                || StringUtils.isEmpty(phoneVerification.getPhone()))
            throw new BadRequestException("Invalid phone number or country phone code.");

        try {
            TMNewMessage message = restClient.getResource(TMNewMessage.class);
            message.setFrom(phoneNumber);
            message.setText(String.format(StringUtils.contains(user.getLanguage(), "en")
                            ? messageEn : messageEs,
                    phoneVerification.getCode()));
            message.setPhones(Collections.singletonList(phoneVerification.getPhoneCountryCode()
                    + phoneVerification.getPhone()));

            message.send();
        } catch (final RestException e) {
            logger.error("Error sending phone verification.", e);
            logAndThrow("Error sending phone verification.");
        }
    }

    @Override
    public void verify(User user, PhoneVerification phoneVerification) throws Exception {
        if (phoneVerification == null || StringUtils.isEmpty(phoneVerification.getPhoneCountryCode())
                || StringUtils.isEmpty(phoneVerification.getPhone()))
            throw new BadRequestException("Invalid phone number or country phone code or verification code.");

        if (!StringUtils.equals(user.getPhoneVerificationCode(), phoneVerification.getCode())) {
            logAndThrow("Error verifying phone verification code.");
        }
    }

    private void logAndThrow(String message) {
        logger.warn(message);
        throw new PhoneVerificationException(message);
    }
}
