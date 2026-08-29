package com.synechisveltiosi.apis.app365.users.event;

import com.synechisveltiosi.apis.app365.notifications.Notification;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.ApplicationEvent;

public class NotificationEvent extends ApplicationEvent {

    private final Notification notification;

    public NotificationEvent(Object source, Notification notification) {
        super(source);

        this.notification = notification;
    }

    public Notification getNotification() {
        return notification;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("notification", notification)
                .toString();
    }
}
