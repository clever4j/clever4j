package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.valuetype.serializable.StringSerializable;
import jakarta.annotation.Nullable;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

@AllNonnullByDefault
public abstract class LocaleValueTemplate<T extends LocaleValueTemplate<T>> implements Comparable<T>, StringSerializable {

    private final static Comparator<String> STRING_COMPARATOR = Comparator.nullsLast(String::compareTo);

    @Nullable
    private final Locale value;

    protected LocaleValueTemplate(@Nullable Locale value) {
        this.value = value;
    }

    protected abstract T createObject(@Nullable Locale value);

    @Nullable
    public Locale getNullableValue() {
        return value;
    }

    public String getStringValue() {
        return value == null ? "" : value.toString();
    }

    public boolean isNull() {
        return value == null;
    }

    @Nullable
    public Locale getValue() {
        return value;
    }

    public Locale requireValue() {
        return Objects.requireNonNull(value);
    }

    @Nullable
    @Override
    public String serializeToString() {
        return value == null ? null : value.toString();
    }

    @Override
    public int compareTo(T value) {
        return STRING_COMPARATOR.compare(value.getStringValue(), getStringValue());
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public boolean equals(@Nullable Locale value) {
        return Objects.equals(this.value, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocaleValueTemplate<?> that = (LocaleValueTemplate<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}