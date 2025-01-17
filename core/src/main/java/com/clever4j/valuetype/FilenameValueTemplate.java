package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

import java.util.Comparator;
import java.util.Objects;

@AllNonnullByDefault
public abstract class FilenameValueTemplate<T extends FilenameValueTemplate<T>> implements Comparable<T> {

    private final static Comparator<String> STRING_COMPARATOR = Comparator.nullsLast(String::compareTo);

    @Nullable
    private final String value;

    protected FilenameValueTemplate(@Nullable String value, boolean strip) {
        if (value != null && strip) {
            value = value.strip();
        }

        this.value = value;
    }

    protected abstract T createObject(@Nullable String value);

    @Nullable
    public String getNullableValue() {
        return value;
    }

    public String getStringValue() {
        return value == null ? "" : value;
    }

    public boolean isNull() {
        return value == null;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    public String requireValue() {
        return Objects.requireNonNull(value);
    }

    @Nullable
    public String getNullIfEmpty() {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return value;
        }
    }

    @Override
    public int compareTo(T value) {
        return STRING_COMPARATOR.compare(this.value, value.getNullableValue());
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public boolean isEmpty() {
        return value == null || value.isEmpty();
    }

    public boolean equals(@Nullable String value) {
        return Objects.equals(this.value, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilenameValueTemplate<?> that = (FilenameValueTemplate<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}