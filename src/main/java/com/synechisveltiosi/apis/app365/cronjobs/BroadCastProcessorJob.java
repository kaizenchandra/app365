package com.synechisveltiosi.apis.app365.cronjobs;

import com.synechisveltiosi.apis.app365.broadcast.Broadcast;
import com.synechisveltiosi.apis.app365.broadcast.BroadcastService;
import com.synechisveltiosi.apis.app365.notifications.Notification;
import com.synechisveltiosi.apis.app365.users.event.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.synechisveltiosi.apis.app365.notifications.Notification.NOTIFICATION_INFO_PLAIN;

/**
 * @author Clivens Petit <clivens.petit@magicsoftbay.com>
 */
@Component
public class BroadCastProcessorJob {

    private static final Logger logger = LoggerFactory.getLogger(BroadCastProcessorJob.class);

    private final BroadcastService broadcastService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public BroadCastProcessorJob(BroadcastService broadcastService, ApplicationEventPublisher publisher) {
        this.broadcastService = broadcastService;
        this.publisher = publisher;
    }

    // TODO THIS JOB IS REALLY BAD IN TERM OF PERFORMANCE, THIS SHOULD REDESIGN AND COMPLETELY AND OPTIMIZED
    @Scheduled(fixedRateString = "${app365.cronjobs.cron-job-run-frequency}",
            initialDelayString = "${app365.cronjobs.cron-job-run-initial-delay}")
    public void processBroadcastInQueue() {
        // Load pending tasks
        List<Broadcast> broadcasts = broadcastService.findAllPending();

        // Break the process if there is no pending tasks
        if (broadcasts.isEmpty()) {
            logger.info("Aborting, no pending broadcast tasks to process.");
            return;
        }

        // Process pending tasks
        for (Broadcast broadcast : broadcasts) {
            try {
                // Create notification
                Notification notification = new Notification();
                notification.setType(NOTIFICATION_INFO_PLAIN);
                notification.setTitle(broadcast.getTitle());
                notification.setContent(broadcast.getContent());
                notification.setTarget(broadcast.getTarget());

                publisher.publishEvent(new NotificationEvent(this, notification));

                broadcast.setStatus(Broadcast.Status.SENT);
                broadcastService.update(broadcast);
            } catch (Exception ex) {
                logger.error("Failed to process broadcast tasks with id: {}", broadcast.getId(), ex);

                try {
                    // Change email task status to failed
                    broadcast.setStatus(Broadcast.Status.FAILED);
                    broadcastService.update(broadcast);
                } catch (Exception e) {
                    logger.error("Failed to update email task status with id: {}", broadcast.getId(), e);
                }
            }
        }
    }
}
