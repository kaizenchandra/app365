package com.synechisveltiosi.apis.app365.users.listener;

import com.synechisveltiosi.apis.app365.users.event.UserActionOccurredEvent;
import com.synechisveltiosi.apis.app365.users.service.AwardService;
import com.synechisveltiosi.apis.app365.users.service.UserPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.validation.constraints.NotNull;

@Component
public class UserActionOccurredListener {

    private final static Logger logger = LoggerFactory.getLogger(UserActionOccurredListener.class);

    private final UserPointService userPointService;
    private final AwardService awardService;

    @Autowired
    public UserActionOccurredListener(UserPointService userPointService, AwardService awardService) {
        this.userPointService = userPointService;
        this.awardService = awardService;
    }

    @Async
    @TransactionalEventListener
    public void onUserActionOccurredEvent(@NotNull UserActionOccurredEvent event) {
        logger.info("User action event: " + event.toString());

        // Try to award the user points
        for (String actionTypeName : event.getActionTypeName()) {
            userPointService.calculatePoints(event.getUserId(), actionTypeName);
        }

        // Try to award the user medals
        awardService.determineUserMedals(event.getUserId());
    }
}
