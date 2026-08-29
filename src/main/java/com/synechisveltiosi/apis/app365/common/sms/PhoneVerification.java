package com.synechisveltiosi.apis.app365.common.sms;

public class PhoneVerification {

    private String phoneCountryCode;
    private String phone;
    private String email;
    private String code;
    private PhoneVerificationChannel channel = PhoneVerificationChannel.SMS;

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PhoneVerificationChannel getChannel() {
        return channel;
    }

    public void setChannel(PhoneVerificationChannel channel) {
        this.channel = channel;
    }
}
