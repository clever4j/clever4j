package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AllNonnullByDefault
public final class UuidValue extends UuidValueTemplate<UuidValue> {

    private static final UuidValue NULL_INSTANCE = new UuidValue(null);
    private static final Map<UUID, UuidValue> INSTANCES = new ConcurrentHashMap<>();

    private UuidValue(@Nullable UUID value) {
        super(value);
    }

    public static UuidValue of(@Nullable UUID value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return INSTANCES.computeIfAbsent(value, UuidValue::new);
        }
    }

    @Override
    protected UuidValue createObject(@Nullable UUID value) {
        return of(value);
    }
}
