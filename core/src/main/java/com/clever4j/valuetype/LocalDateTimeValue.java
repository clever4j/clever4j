package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.valuetype.serializable.LocalDateTimeSerializable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

@AllNonnullByDefault
public abstract class LocalDateTimeValue<T extends LocalDateTimeValue<T>> implements LocalDateTimeSerializable, Comparable<T> {

    @Nullable
    private final LocalDateTime value;

    protected LocalDateTimeValue(@Nullable LocalDateTime value) {
        this.value = value;
    }

    protected abstract T createObject(@Nullable LocalDateTime value);

    @Override
    @Nullable
    public LocalDateTime serializeToLocalDateTime() {
        return value;
    }

    public LocalDateTime requireValue() {
        if (this.value == null) {
            throw new IllegalStateException("Value has not been set");
        }

        return value;
    }

    public LocalDateTime getValueOrDefault(LocalDateTime value) {
        if (this.value == null) {
            return value;
        }

        return this.value;
    }

    @Override
    public int compareTo(T time) {
        if (this.value == null) {
            return -1;
        }

        return this.value.compareTo(time.requireValue());
    }

    @Nonnull
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalDateTimeValue<?> that = (LocalDateTimeValue<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
