package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllNonnullByDefault
public final class MimeTypeValue extends MimeTypeValueTemplate<MimeTypeValue> {

    private static final MimeTypeValue NULL_INSTANCE = new MimeTypeValue(null);
    private static final Map<String, MimeTypeValue> INSTANCES = new ConcurrentHashMap<>();

    private MimeTypeValue(@Nullable String value) {
        super(value);
    }

    public static MimeTypeValue of(@Nullable String value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return INSTANCES.computeIfAbsent(value, MimeTypeValue::new);
        }
    }

    @Override
    protected MimeTypeValue createObject(@Nullable String value) {
        return of(value);
    }
}
