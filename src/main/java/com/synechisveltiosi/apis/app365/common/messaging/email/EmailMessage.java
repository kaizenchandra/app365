package com.synechisveltiosi.apis.app365.common.messaging.email;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public interface EmailMessage {

    InternetAddress getFrom();

    InternetAddress getReplyTo();

    List<InternetAddress> getTo();

    List<InternetAddress> getCc();

    List<InternetAddress> getBcc();

    String getSubject();

    String getBody();

    List<File> getAttachments();

    Charset getCharset();

    Locale getLocale();

    Date getDeliveryDate();
}
