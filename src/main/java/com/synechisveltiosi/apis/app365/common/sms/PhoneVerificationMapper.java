package com.synechisveltiosi.apis.app365.common.sms;

import com.synechisveltiosi.apis.app365.common.sms.dto.VerifyPhoneVerificationCodeRequest;
import com.synechisveltiosi.apis.app365.users.dto.ResendPhoneVerificationCodeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhoneVerificationMapper {

    PhoneVerificationMapper INSTANCE = Mappers.getMapper(PhoneVerificationMapper.class);

    PhoneVerification from(VerifyPhoneVerificationCodeRequest phoneVerificationCode);

    PhoneVerification from(ResendPhoneVerificationCodeRequest phoneVerificationCode);
}
