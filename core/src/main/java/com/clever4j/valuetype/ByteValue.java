package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllNonnullByDefault
public final class ByteValue extends LongValueTemplate<ByteValue> {

    private static final ByteValue NULL_INSTANCE = new ByteValue(null);
    private static final Map<Long, ByteValue> INSTANCES = new ConcurrentHashMap<>();

    private ByteValue(@Nullable Long value) {
        super(value);
    }

    public static ByteValue of(@Nullable Long value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return INSTANCES.computeIfAbsent(value, ByteValue::new);
        }
    }

    @Override
    protected ByteValue createObject(@Nullable Long value) {
        return of(value);
    }
}
