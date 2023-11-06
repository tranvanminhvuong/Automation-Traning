package com.nashtech.assetmanagement.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    final static String DEFAULT_DATE_TIME = "dd MMM yyyy";

    public static String getCurrentDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    public static LocalDate convertDateStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME, Locale.ENGLISH);
        return LocalDate.parse(date, formatter);
    }

    public static String convertDateStringToDateStringByFormat(String dateString, String dateFormat) {
        LocalDate date = convertDateStringToLocalDate(dateString);
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern(dateFormat);
        return date.format(expectedFormatter);
    }
}
