package com.synechisveltiosi.apis.app365.notifications.mapper;

import com.synechisveltiosi.apis.app365.notifications.Notification;
import com.synechisveltiosi.apis.app365.notifications.NotificationRequest;
import com.synechisveltiosi.apis.app365.notifications.NotificationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper {

    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mappings({
            @Mapping(source = "notificationId", target = "id"),
            @Mapping(source = "userId", target = "owner")
    })
    NotificationResponse from(Notification notification);

    Notification from(NotificationRequest notificationRequest);
}
