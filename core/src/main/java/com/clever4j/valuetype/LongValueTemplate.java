package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nonnull;

import java.util.Objects;

@AllNonnullByDefault
public abstract class LongValueTemplate<T extends LongValueTemplate<T>> implements Comparable<T> {

    private final Long value;

    protected LongValueTemplate(Long value) {
        this.value = value;
    }

    public static <T extends LongValueTemplate<?>> T sum(ObjectFactory<T> objectFactory, T... values) {
        long sum = 0;

        for (T value : values) {
            sum += value.getValue();
        }

        return objectFactory.create(sum);
    }

    protected abstract T createObject(Long value);

    public Long getValue() {
        return value;
    }

    @Override
    public int compareTo(T time) {
        return Long.compare(this.value, time.getValue());
    }

    @Nonnull
    public T add(T value) {
        return createObject(getValue() + value.getValue());
    }

    @Nonnull
    public T multiple(T value) {
        return createObject(getValue() * value.getValue());
    }

    @Nonnull
    public T multiple(Long value) {
        return createObject(getValue() * value);
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
        LongValueTemplate<?> that = (LongValueTemplate<?>) o;
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
