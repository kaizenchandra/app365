package com.synechisveltiosi.apis.app365.common.messaging.cloud;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface PushNotificationService {

    String send(@NotBlank String to, @NotNull NotificationOptions notification);
}
