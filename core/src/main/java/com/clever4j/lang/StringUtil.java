package com.clever4j.lang;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

@AllNonnullByDefault
public class StringUtil {

    private StringUtil() {

    }

    public static String truncate(String value, int maxLength, boolean dots) {
        Objects.requireNonNull(value, "truncated value cannot be null");

        if (value.length() <= maxLength) {
            return value;
        }

        if (dots) {
            return value.substring(0, maxLength) + "...";
        } else {
            return value.substring(0, maxLength);
        }
    }

    public static String concat(String delimiter, String... values) {
        StringBuilder builder = new StringBuilder();

        for (String value : values) {
            if (value == null || value.isEmpty()) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append(delimiter);
            }

            builder.append(value);
        }

        return builder.toString();
    }

    @Nonnull
    public static String concat(String delimiter, List<String> values) {
        StringBuilder builder = new StringBuilder();

        for (String value : values) {
            if (value == null || value.isEmpty()) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append(delimiter);
            }

            builder.append(value);
        }

        return builder.toString();
    }
}
