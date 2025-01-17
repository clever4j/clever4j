package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllNonnullByDefault
public final class MimeTypeValue extends MimeTypeValueTemplate<MimeTypeValue> {

    private static final MimeTypeValue NULL_INSTANCE = new MimeTypeValue(null);
    private static final Map<MimeType, MimeTypeValue> INSTANCES = new ConcurrentHashMap<>();

    private MimeTypeValue(@Nullable MimeType value) {
        super(value);
    }

    public static MimeTypeValue of(@Nullable MimeType value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return INSTANCES.computeIfAbsent(value, MimeTypeValue::new);
        }
    }

    @Override
    protected MimeTypeValue createObject(@Nullable MimeType value) {
        return of(value);
    }
}
