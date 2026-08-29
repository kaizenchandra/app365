package com.synechisveltiosi.apis.app365.common.sms;

import com.synechisveltiosi.apis.app365.users.entity.User;

public interface PhoneVerificationService {

    void sendVerificationCode(User user, PhoneVerification phoneVerification) throws Exception;

    void verify(User user, PhoneVerification phoneVerification) throws Exception;
}
