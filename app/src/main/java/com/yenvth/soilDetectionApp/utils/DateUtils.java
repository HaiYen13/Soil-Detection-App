package com.yenvth.soilDetectionApp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String convertTimes(long timestamp, String format) {
        Date date = new Date(timestamp);

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String formattedDate = sdf.format(date);

        return formattedDate;
    }
}
