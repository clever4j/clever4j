package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllNonnullByDefault
public final class LongValue extends LongValueTemplate<LongValue> {

    private static final LongValue NULL_INSTANCE = new LongValue(null);
    private static final Map<Long, LongValue> INSTANCES = new ConcurrentHashMap<>();

    private LongValue(@Nullable Long value) {
        super(value);
    }

    public static LongValue of(@Nullable Long value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return INSTANCES.computeIfAbsent(value, LongValue::new);
        }
    }

    @Override
    protected LongValue createObject(@Nullable Long value) {
        return of(value);
    }
}
