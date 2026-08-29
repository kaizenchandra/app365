package com.synechisveltiosi.apis.app365.notifications;

import com.synechisveltiosi.apis.app365.notifications.mapper.NotificationMapper;
import com.synechisveltiosi.apis.app365.users.event.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.synechisveltiosi.apis.app365.notifications.Notification.NOTIFICATION_INFO_PLAIN;

@RestController
@RequestMapping(value = "/notifications",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {

    private final ApplicationEventPublisher publisher;

    @Autowired
    public NotificationController(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping
    public ResponseEntity<Void> send(@Valid @RequestBody NotificationRequest notificationRequest) {
        Notification notification = NotificationMapper.INSTANCE.from(notificationRequest);
        notification.setType(NOTIFICATION_INFO_PLAIN);

        publisher.publishEvent(new NotificationEvent(this, notification));

        return ResponseEntity.noContent().build();
    }
}
