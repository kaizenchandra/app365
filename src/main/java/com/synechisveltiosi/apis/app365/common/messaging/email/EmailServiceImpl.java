package com.synechisveltiosi.apis.app365.common.messaging.email;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final it.ozimov.springboot.mail.service.EmailService emailService;

    @Autowired
    public EmailServiceImpl(it.ozimov.springboot.mail.service.EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    @Override
    public void send(@NotNull EmailMessage message) throws Exception {
        // Build email
        final Email email = DefaultEmail.builder()
                .from(message.getFrom())
                .to(message.getTo())
                .subject(message.getSubject())
                .body(message.getBody())
                .encoding(message.getCharset().displayName())
                .build();

        // Send the email
        emailService.send(email);
    }

    @Async
    @Override
    public void send(@NotNull EmailMessage message, @NotNull String templatePath) throws Exception {
        this.send(message, templatePath, new HashMap<>());
    }

    @Async
    @Override
    public void send(@NotNull EmailMessage message, @NotNull String templatePath,
                     @NotNull Map<String, Object> templateAttributes) throws Exception {

        // Build email
        final Email email = DefaultEmail.builder()
                .from(message.getFrom())
                .to(message.getTo())
                .subject(message.getSubject())
                .body(message.getBody())
                .encoding(message.getCharset().displayName())
                .build();

        // Send the email
        emailService.send(email, templatePath, templateAttributes);
    }
}
