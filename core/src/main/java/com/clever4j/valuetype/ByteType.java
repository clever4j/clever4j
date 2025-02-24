package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

@AllNonnullByDefault
public class ByteType {

    @Nullable
    private final Integer value;

    private ByteType(@Nullable Integer value) {
        this.value = value;
    }

    public static ByteType of(@Nullable Integer value) {
        return new ByteType(value);
    }

    public double getMegaBytes() {
        return value == null ? 0 : (double) value / (1024 * 1024);
    }

    public double getGigaBytes() {
        return value == null ? 0 : (double) value / (1024 * 1024 * 1024);
    }

    @Nullable
    public Integer getValue() {
        return value;
    }

    public boolean isNull() {
        return value == null;
    }
}
