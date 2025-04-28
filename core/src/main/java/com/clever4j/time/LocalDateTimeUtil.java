package com.clever4j.time;

import com.clever4j.lang.AllNonnullByDefault;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllNonnullByDefault
public final class LocalDateTimeUtil {

    private LocalDateTimeUtil() {
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
}
