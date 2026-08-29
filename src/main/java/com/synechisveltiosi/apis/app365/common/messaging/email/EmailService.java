package com.synechisveltiosi.apis.app365.common.messaging.email;

import javax.validation.constraints.NotNull;
import java.util.Map;

public interface EmailService {

    void send(@NotNull EmailMessage message) throws Exception;

    void send(@NotNull EmailMessage message, @NotNull String templatePath) throws Exception;

    void send(@NotNull EmailMessage message,
              @NotNull String templatePath,
              @NotNull Map<String, Object> templateAttributes) throws Exception;
}
