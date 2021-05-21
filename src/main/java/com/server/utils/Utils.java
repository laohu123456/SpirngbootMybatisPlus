package com.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Utils {
    private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getCurrentDate(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    public static String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return simpleDateFormat.format(new Date());
    }

}
