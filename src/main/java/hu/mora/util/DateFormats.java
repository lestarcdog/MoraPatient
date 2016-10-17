package hu.mora.util;

import java.time.format.DateTimeFormatter;

public class DateFormats {

    public static final DateTimeFormatter DAY = DateTimeFormatter.ISO_DATE;
    public static final DateTimeFormatter DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
