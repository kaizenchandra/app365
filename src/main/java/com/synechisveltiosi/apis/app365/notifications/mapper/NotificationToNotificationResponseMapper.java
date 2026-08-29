package com.synechisveltiosi.apis.app365.notifications.mapper;

import com.synechisveltiosi.apis.app365.notifications.NotificationResponse;
import com.synechisveltiosi.apis.app365.common.util.mapper.AbstractMapper;
import com.synechisveltiosi.apis.app365.notifications.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationToNotificationResponseMapper extends AbstractMapper<Notification, NotificationResponse> {

    @Override
    public NotificationResponse map(Notification notification) {
        return NotificationMapper.INSTANCE.from(notification);
    }
}
