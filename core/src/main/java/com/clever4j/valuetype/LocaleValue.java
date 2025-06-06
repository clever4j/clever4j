package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllNonnullByDefault
public final class LocaleValue extends LocaleValueTemplate<LocaleValue> {

    private static final LocaleValue NULL_INSTANCE = new LocaleValue(null);

    private static final Map<Locale, LocaleValue> INSTANCES = new ConcurrentHashMap<>();
    private static final Map<String, LocaleValue> STRING_INSTANCES = new ConcurrentHashMap<>();

    private LocaleValue(@Nullable Locale value) {
        super(value);
    }

    public static LocaleValue of(@Nullable Locale value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return INSTANCES.computeIfAbsent(value, LocaleValue::new);
        }
    }

    public static LocaleValue of(@Nullable String value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return STRING_INSTANCES.computeIfAbsent(value, s -> {
                String[] tokens = s.split("_");

                return new LocaleValue(Locale.of(tokens[0], tokens[1]));
            });
        }
    }

    @Override
    protected LocaleValue createObject(@Nullable Locale value) {
        return of(value);
    }
}
