package com.synechisveltiosi.apis.app365.common.util.date;

import org.apache.commons.lang3.time.FastDateFormat;

public class DateFormatUtils {

    /**
     * Long date time format (yyyy-MM-dd'T'HH:mm:ss)
     */
    public static final String ISO_8601_DATETIME_STRING_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * Long date time format with time zone (yyyy-MM-dd'T'HH:mm:ssZZ)
     */
    public static final String ISO_8601_DATETIME_TIME_ZONE_STRING_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZZ";

    /**
     * Short date time format (yyyy-MM-dd)
     */
    public static final String ISO_8601_DATE_STRING_FORMAT = "yyyy-MM-dd";

    /**
     * Time format (HH:mm:ss)
     */
    public static final String ISO_8601_TIME_STRING_FORMAT = "HH:mm:ss";

    /**
     * Time format with time zone (HH:mm:ssZZ)
     */
    public static final String ISO_8601_TIME_TIME_ZONE_STRING_FORMAT = "HH:mm:ssZZ";

    /**
     * Long date time format (yyyy-MM-dd'T'HH:mm:ss)
     */
    public static final FastDateFormat ISO_8601_DATETIME_FORMAT =
            org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT;

    /**
     * Long date time format with time zone (yyyy-MM-dd'T'HH:mm:ssZZ)
     */
    public static final FastDateFormat ISO_8601_DATETIME_TIME_ZONE_FORMAT =
            org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT;

    /**
     * Short date time format (yyyy-MM-dd)
     */
    public static final FastDateFormat ISO_8601_DATE_FORMAT =
            org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT;

    /**
     * Time format (HH:mm:ss)
     */
    public static final FastDateFormat ISO_8601_TIME_FORMAT =
            org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_TIME_FORMAT;

    /**
     * Time format with time zone (HH:mm:ssZZ)
     */
    public static final FastDateFormat ISO_8601_TIME_TIME_ZONE_FORMAT =
            org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_TIME_TIME_ZONE_FORMAT;

}
