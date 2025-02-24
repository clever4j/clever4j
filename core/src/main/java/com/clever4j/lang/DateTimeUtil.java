package com.clever4j.lang;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@AllNonnullByDefault
public class DateTimeUtil {

    private DateTimeUtil() {

    }

    public static LocalDate toLocalDate(float epochSeconds) {
        return LocalDate.ofEpochDay((long) epochSeconds);
    }

    public static LocalDate toLocalDate(long epochSeconds) {
        return LocalDate.ofEpochDay(epochSeconds);
    }

    public static boolean isBetweenEqual(LocalDateTime value, LocalDateTime from, LocalDateTime to) {
        return (value.equals(from) || value.isAfter(from)) &&
            (value.isBefore(to) || value.isEqual(to));
    }

    public static LocalDate toLocalDate(Calendar calendar) {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Calendar calendar) {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }

    public static Date toDate(Calendar calendar) {
        return calendar.getTime();
    }

    public static long toEpochSecond(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    public static long toEpochSecond(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }
}
