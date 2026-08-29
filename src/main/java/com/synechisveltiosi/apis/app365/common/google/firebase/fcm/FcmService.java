package com.synechisveltiosi.apis.app365.common.google.firebase.fcm;

import com.synechisveltiosi.apis.app365.common.messaging.cloud.NotificationOptions;
import com.synechisveltiosi.apis.app365.common.messaging.cloud.PushNotificationService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class FcmService implements PushNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(FcmService.class);
    private static final List<String> FCM_API_ERRORS = new ArrayList<>();

    static {
        FCM_API_ERRORS.add("messaging/invalid-argument");
        FCM_API_ERRORS.add("messaging/invalid-recipient");
        FCM_API_ERRORS.add("messaging/invalid-payload");
        FCM_API_ERRORS.add("messaging/invalid-data-payload-key");
        FCM_API_ERRORS.add("messaging/payload-size-limit-exceeded");
        FCM_API_ERRORS.add("messaging/invalid-options");
        FCM_API_ERRORS.add("messaging/invalid-registration-token");
        FCM_API_ERRORS.add("messaging/registration-token-not-registered");
        FCM_API_ERRORS.add("messaging/invalid-package-name");
        FCM_API_ERRORS.add("messaging/message-rate-exceeded");
        FCM_API_ERRORS.add("messaging/device-message-rate-exceeded");
        FCM_API_ERRORS.add("messaging/topics-message-rate-exceeded");
        FCM_API_ERRORS.add("messaging/too-many-topics");
        FCM_API_ERRORS.add("messaging/invalid-apns-credentials");
        FCM_API_ERRORS.add("messaging/mismatched-credential");
        FCM_API_ERRORS.add("messaging/authentication-error");
        FCM_API_ERRORS.add("messaging/server-unavailable");
        FCM_API_ERRORS.add("messaging/internal-error");
        FCM_API_ERRORS.add("messaging/unknown-error");
    }

    @Autowired
    public FcmService(FirebaseOptions firebaseOptions) {
        FirebaseApp.initializeApp(firebaseOptions);
    }

    @Override
    public String send(@NotBlank String to, @NotNull NotificationOptions notification) {
        try {
            Notification notification1 = new Notification(notification.getTitle(), notification.getDescription());

            // Firebase message
            // projects/app365/messages/1531181886298329
            // projects/app365/messages/1531182074776848
            // (?<=messages\/)[0-9]+
            Message message = Message.builder()
                    .putData("title", notification.getTitle())
                    .putData("description", notification.getDescription())
                    .setNotification(notification1)
                    .setToken(to)
                    .build();

            // Send a message to the device corresponding to the provided registration token.
            return FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException ex) {
            // Log firebase error message
            logger.error(ex.getMessage(), ex);

            return null;
        }
    }
}
