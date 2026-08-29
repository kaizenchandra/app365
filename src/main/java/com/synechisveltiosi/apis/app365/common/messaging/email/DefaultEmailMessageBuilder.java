package com.synechisveltiosi.apis.app365.common.messaging.email;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class DefaultEmailMessageBuilder {
    private InternetAddress from;
    private InternetAddress replyTo;
    private List<InternetAddress> to;
    private List<InternetAddress> cc;
    private List<InternetAddress> bcc;
    private String subject;
    private String body;
    private List<File> attachments;
    private Charset charset = StandardCharsets.UTF_8;
    private Locale locale = Locale.US;
    private Date deliveryDate;

    private DefaultEmailMessageBuilder() {

    }

    public static DefaultEmailMessageBuilder builder() {
        return new DefaultEmailMessageBuilder();
    }

    public DefaultEmailMessageBuilder withFrom(InternetAddress from) {
        this.from = from;
        return this;
    }

    public DefaultEmailMessageBuilder withReplyTo(InternetAddress replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public DefaultEmailMessageBuilder withTo(List<InternetAddress> to) {
        this.to = to;
        return this;
    }

    public DefaultEmailMessageBuilder withCc(List<InternetAddress> cc) {
        this.cc = cc;
        return this;
    }

    public DefaultEmailMessageBuilder withBcc(List<InternetAddress> bcc) {
        this.bcc = bcc;
        return this;
    }

    public DefaultEmailMessageBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public DefaultEmailMessageBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public DefaultEmailMessageBuilder withAttachments(List<File> attachments) {
        this.attachments = attachments;
        return this;
    }

    public DefaultEmailMessageBuilder withCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public DefaultEmailMessageBuilder withLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public DefaultEmailMessageBuilder withDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public DefaultEmailMessage build() {
        DefaultEmailMessage defaultEmailMessage = new DefaultEmailMessage();
        defaultEmailMessage.setFrom(from);
        defaultEmailMessage.setReplyTo(replyTo);
        defaultEmailMessage.setTo(to);
        defaultEmailMessage.setCc(cc);
        defaultEmailMessage.setBcc(bcc);
        defaultEmailMessage.setSubject(subject);
        defaultEmailMessage.setBody(body);
        defaultEmailMessage.setAttachments(attachments);
        defaultEmailMessage.setCharset(charset);
        defaultEmailMessage.setLocale(locale);
        defaultEmailMessage.setDeliveryDate(deliveryDate);
        return defaultEmailMessage;
    }
}
