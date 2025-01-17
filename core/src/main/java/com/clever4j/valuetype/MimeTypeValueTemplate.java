package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Comparator;
import java.util.Objects;

@AllNonnullByDefault
public abstract class MimeTypeValueTemplate<T extends MimeTypeValueTemplate<T>> implements Comparable<T> {

    private final static Comparator<String> STRING_COMPARATOR = Comparator.nullsLast(String::compareTo);
    @Nullable
    private final MimeType value;

    protected MimeTypeValueTemplate(@Nullable MimeType value) {
        this.value = value;
    }

    protected abstract T createObject(@Nullable MimeType value);

    public MimeType requireValue() {
        if (value == null) {
            throw new IllegalStateException("value not set");
        }

        return value;
    }

    @Nullable
    public MimeType getValue() {
        return value;
    }

    public MimeType getValueOrDefault(MimeType defaultValue) {
        return value == null ? defaultValue : value;
    }

    @Override
    public int compareTo(T value) {
        return STRING_COMPARATOR.compare(
            this.value == null ? null : this.value.name(),
            value.getValue() == null ? null : value.getValue().name()
        );
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
        MimeTypeValueTemplate<?> that = (MimeTypeValueTemplate<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
