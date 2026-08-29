package com.synechisveltiosi.apis.app365.common.util.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class DateConverter {

    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime fromDate(Date date) {
        if (date == null) return null;

        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
