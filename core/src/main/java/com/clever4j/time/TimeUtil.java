package com.clever4j.time;

import com.clever4j.lang.AllNonnullByDefault;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllNonnullByDefault
public final class TimeUtil {

    private TimeUtil() {
    }

    public static long toEpochSecond(LocalDateTime value) {
        return value.atZone(ZoneId.systemDefault())
            .toInstant()
            .getEpochSecond();
    }

    public static long toEpochMilli(LocalDateTime value) {
        return value.atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli();
    }

    public static boolean isBefore(LocalDateTime value, LocalDateTime reference) {
        return value.isBefore(reference);
    }

    public static boolean isBeforeEqual(LocalDateTime value, LocalDateTime reference) {
        return value.isBefore(reference) || value.isEqual(reference);
    }

    public static boolean isAfter(LocalDateTime value, LocalDateTime reference) {
        return value.isAfter(reference);
    }

    public static boolean isAfterEqual(LocalDateTime value, LocalDateTime reference) {
        return value.isAfter(reference) || value.isEqual(reference);
    }

    public static boolean isBetween(LocalDateTime value, LocalDateTime start, LocalDateTime end) {
        return (value.isAfter(start) || value.isEqual(start)) &&
            (value.isBefore(end) || value.isEqual(end));
    }
}