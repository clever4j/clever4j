package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllNonnullByDefault
public final class IntegerValue extends IntegerValueTemplate<IntegerValue> {

    private static final IntegerValue NULL_INSTANCE = new IntegerValue(null);
    private static final Map<Integer, IntegerValue> INSTANCES = new ConcurrentHashMap<>();

    private IntegerValue(@Nullable Integer value) {
        super(value);
    }

    public static IntegerValue of(@Nullable Integer value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return INSTANCES.computeIfAbsent(value, IntegerValue::new);
        }
    }

    @Override
    protected IntegerValue createObject(@Nullable Integer value) {
        return of(value);
    }
}
