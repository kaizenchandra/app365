package com.synechisveltiosi.apis.app365.users.listener;

import com.synechisveltiosi.apis.app365.common.messaging.cloud.NotificationOptions;
import com.synechisveltiosi.apis.app365.common.messaging.cloud.PushNotificationService;
import com.synechisveltiosi.apis.app365.devices.Device;
import com.synechisveltiosi.apis.app365.devices.DeviceService;
import com.synechisveltiosi.apis.app365.notifications.Notification;
import com.synechisveltiosi.apis.app365.notifications.NotificationService;
import com.synechisveltiosi.apis.app365.notifications.NotificationTarget;
import com.synechisveltiosi.apis.app365.users.event.NotificationEvent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationListener implements ApplicationListener<NotificationEvent> {

    private final static Logger LOGGER = LoggerFactory.getLogger(NotificationListener.class);

    private final NotificationService notificationService;
    private final PushNotificationService pushNotificationService;
    private final DeviceService deviceService;

    @Autowired
    public NotificationListener(NotificationService notificationService,
                                PushNotificationService pushNotificationService, DeviceService deviceService) {

        this.notificationService = notificationService;
        this.pushNotificationService = pushNotificationService;
        this.deviceService = deviceService;
    }

    // TODO Make notification asynchronously
    @Override
    public void onApplicationEvent(NotificationEvent event) {
        LOGGER.info("Notification event: " + event.toString());

        List<Device> devices = deviceService.findAll();
        for (Device device : devices) {
            Notification notification = event.getNotification();
            if (!(NotificationTarget.ALL.name().equalsIgnoreCase(notification.getTarget().name())
                    || device.getPlatform().equalsIgnoreCase(notification.getTarget().name()))) {
                continue;
            }

            notification.setUserId(device.getUserId());

            // Save notification
            Notification newNotification = notificationService.save(notification);

            // Dispatch push notification
            NotificationOptions notificationOptions = new NotificationOptions();
            notificationOptions.setTitle(newNotification.getTitle());
            notificationOptions.setDescription(newNotification.getContent());

            if (!StringUtils.isBlank(device.getFirebaseToken())) {
                pushNotificationService.send(device.getFirebaseToken(), notificationOptions);
            }
        }
    }

//    @Async
//    @TransactionalEventListener
//    public void onNotificationEvent(@NotNull NotificationEvent event) {
//        LOGGER.info("Notification event: " + event.toString());
//
//        // Save notification
//        Notification notification = notificationService.save(event.getNotification());
//
//        // Dispatch push notification
//        NotificationOptions notificationOptions = new NotificationOptions();
//        notificationOptions.setTitle(notification.getTitle());
//        notificationOptions.setDescription(notification.getContent());
//
//        deviceService.findAll().forEach(device -> {
//            if (!StringUtils.isBlank(device.getFirebaseToken()))
//                pushNotificationService.send(device.getFirebaseToken(), notificationOptions);
//        });
//    }
}
