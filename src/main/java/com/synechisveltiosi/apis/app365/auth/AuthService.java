package com.synechisveltiosi.apis.app365.auth;

import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;

import java.io.IOException;

public interface AuthService {

    void logout(Long userId, String deviceId, AccessToken accessToken) throws IOException;

    void resetPassword(String email);

    void resetPassword(String phoneCountryCode, String phone);

    void setNewPassword(String email, String password, String verificationCode) throws IOException;

    void changePassword(Long userId, String oldPassword, String newPassword) throws IOException;

    String fetchToken() throws IOException;
}
