package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nonnull;

import javax.annotation.Nullable;
import java.util.Objects;

@AllNonnullByDefault
public abstract class ByteValueTemplate<T extends ByteValueTemplate<T>> implements Comparable<T> {

    @Nullable
    private final Long value;

    protected ByteValueTemplate(@Nullable Long value) {
        this.value = value;
    }

    @Nonnull
    public static <T extends ByteValueTemplate<?>> T sum(ObjectFactory<T> objectFactory, T... values) {
        long sum = 0;

        for (T value : values) {
            sum += value.requireValue();
        }

        return objectFactory.create(sum);
    }

    protected abstract T createObject(@Nullable Long value);

    public Long requireValue() {
        if (value == null) {
            throw new IllegalStateException("value not set");
        }

        return value;
    }

    @Nullable
    public Long getValue() {
        return value;
    }

    public Long getValueOrDefault(Long defaultValue) {
        return value == null ? defaultValue : value;
    }

    public Long getValueOrZero() {
        return value == null ? 0 : value;
    }

    @Override
    public int compareTo(T time) {
        return Long.compare(this.value, time.requireValue());
    }

    @Nonnull
    public T add(T value) {
        return createObject(requireValue() + value.requireValue());
    }

    @Nonnull
    public T multiple(T value) {
        return createObject(requireValue() * value.requireValue());
    }

    @Nonnull
    public T multiple(Long value) {
        return createObject(requireValue() * value);
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
        ByteValueTemplate<?> that = (ByteValueTemplate<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    protected interface ObjectFactory<T> {
        T create(Long value);
    }
}
