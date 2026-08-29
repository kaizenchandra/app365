package com.synechisveltiosi.apis.app365.auth;

import com.synechisveltiosi.apis.app365.common.auth.dto.AccessToken;
import com.synechisveltiosi.apis.app365.common.auth.repository.OAuth2Repository;
import com.synechisveltiosi.apis.app365.common.messaging.email.DefaultEmailMessageBuilder;
import com.synechisveltiosi.apis.app365.common.messaging.email.EmailMessage;
import com.synechisveltiosi.apis.app365.common.messaging.email.EmailService;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.BadRequestException;
import com.synechisveltiosi.apis.app365.common.rest.response.exception.MultiStatusException;
import com.synechisveltiosi.apis.app365.common.util.RandomUtils;
import com.synechisveltiosi.apis.app365.config.AppConfig;
import com.synechisveltiosi.apis.app365.devices.DeviceService;
import com.synechisveltiosi.apis.app365.users.entity.User;
import com.synechisveltiosi.apis.app365.users.exception.UserNotFoundException;
import com.synechisveltiosi.apis.app365.users.service.UserService;
import com.google.common.collect.Lists;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final OAuth2Repository oAuth2Repository;
    private final UserService userService;
    private final DeviceService deviceService;
    private final EmailService emailService;
    private final AppConfig appConfig;

    @Autowired
    public AuthServiceImpl(OAuth2Repository oAuth2Repository, UserService userService,
                           DeviceService deviceService, EmailService emailService, AppConfig appConfig) {

        this.oAuth2Repository = oAuth2Repository;
        this.userService = userService;
        this.deviceService = deviceService;
        this.emailService = emailService;
        this.appConfig = appConfig;
    }

    @Override
    public void logout(Long userId, String deviceId, @NotNull AccessToken accessToken) throws IOException {
        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Clear firebase token
        deviceService.clearFirebaseToken(deviceId);

        // Clear user social tokens
        User user = userOptional.get();
        if (user.getMetaData() != null) user.getMetaData().setSocialTokens(null);
        userService.update(user);

        // Make request to destroy user access token
        oAuth2Repository.logout(accessToken);
    }

    @Override
    public void resetPassword(String email) {
        // Find the user
        Optional<User> userOptional = userService.findByEmail(email);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        User user = userOptional.get();

        // Generate and save user verification code
        String code = String.format("%06d", RandomUtils.generate(0, 999999));
        user.setEmailVerificationCode(code);
        userService.update(user);

        try {
            // Build reset password email
            EmailMessage message = DefaultEmailMessageBuilder.builder()
                    .withFrom(new InternetAddress("applepolitical365@gmail.com", "PRM"))
                    .withTo(Lists.newArrayList(new InternetAddress(user.getEmail(),
                            user.getFirstName() + " " + user.getLastName())))
                    .withSubject("Restablecer contraseña")
                    .withBody(String.format("Enter this code in the app to reset your password. <b>%s<b>", code))
                    .build();

            // Email variable
            Map<String, Object> emailTemplateVariables = new HashMap<>();
            emailTemplateVariables.put("name", user.getFullName());
            emailTemplateVariables.put("code", code);

            // Sent user verification code via email
            emailService.send(message, "emails/user-email-verification-code.html", emailTemplateVariables);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);

            // Throw exception to let the user know his action could not be fulfill
            throw new MultiStatusException("Failed to send verification email.");
        }
    }

    @Override
    public void resetPassword(String phoneCountryCode, String phone) {
        // Intentionally left blank
    }

    @Override
    public void setNewPassword(String email, String password, String verificationCode) throws IOException {
        // Validate verification code
        if (StringUtils.isBlank(verificationCode))
            throw new BadRequestException("Verification code cannot be null or blank");

        // Validate the password
        if (StringUtils.isBlank(password)
                || !Pattern.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9!#$%&()*+,.;<=>?@^_~]{6,128}$",
                password)) {

            throw new BadRequestException("Invalid password.");
        }

        // Find the user
        Optional<User> userOptional = userService.findByEmail(email);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        User user = userOptional.get();

        // Make sure the verification token is valid
        if (!verificationCode.equals(user.getEmailVerificationCode())) throw new EmailVerificationException();

        // Update local data
        user.setEmailVerificationCode(null);
        user.setEmailVerified(Boolean.TRUE);
        user.setEmailVerifiedAt(LocalDateTime.now());

        userService.update(user);

        // Make the request to set the new password
        oAuth2Repository.resetUserPassword(user.getAuthUserId(), password);
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) throws IOException {
        // Validate the password
        if (StringUtils.isBlank(newPassword)
                || !Pattern.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9!#$%&()*+,.;<=>?@^_~]{6,128}$",
                newPassword)) {

            throw new BadRequestException("Invalid password.");
        }

        // Find the user
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException();

        // Make the request to change password
        oAuth2Repository.changeUserPassword(userOptional.get().getAuthUserId(), oldPassword, newPassword);
    }

    @Override
    public String fetchToken() throws IOException {

        OkHttpClient client = new OkHttpClient();

        String credential = Credentials.basic("app", "secret");
        FormBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url("http://74147cbe.ngrok.io" + appConfig.getCrm().getPostEndpoints().getToken())
                .post(formBody)
                .addHeader("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();

        String string = response.body().string();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        AccessToken accessToken = gson.fromJson(string, AccessToken.class);
        return accessToken.getAccessToken();
    }
}
