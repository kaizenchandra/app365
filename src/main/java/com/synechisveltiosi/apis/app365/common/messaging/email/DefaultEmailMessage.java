package com.synechisveltiosi.apis.app365.common.messaging.email;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DefaultEmailMessage implements EmailMessage {

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

    @Override
    public InternetAddress getFrom() {
        return from;
    }

    public void setFrom(InternetAddress from) {
        this.from = from;
    }

    @Override
    public InternetAddress getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(InternetAddress replyTo) {
        this.replyTo = replyTo;
    }

    @Override
    public List<InternetAddress> getTo() {
        return to;
    }

    public void setTo(List<InternetAddress> to) {
        this.to = to;
    }

    @Override
    public List<InternetAddress> getCc() {
        return cc;
    }

    public void setCc(List<InternetAddress> cc) {
        this.cc = cc;
    }

    @Override
    public List<InternetAddress> getBcc() {
        return bcc;
    }

    public void setBcc(List<InternetAddress> bcc) {
        this.bcc = bcc;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public List<File> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<File> attachments) {
        this.attachments = attachments;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
