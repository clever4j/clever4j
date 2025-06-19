package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.valuetype.serializable.StringSerializable;
import jakarta.annotation.Nullable;

import java.util.Comparator;
import java.util.Objects;

@AllNonnullByDefault
public abstract class StringValueTemplate<T extends StringValueTemplate<T>> implements NullableValue, Comparable<T>, StringSerializable {

    private final static Comparator<String> STRING_COMPARATOR = Comparator.nullsLast(String::compareTo);

    @Nullable
    private final String value;

    protected StringValueTemplate(
        @Nullable String value,
        boolean strip,
        boolean cleanWhitespace,
        boolean cleanNonAlphanumeric
    ) {
        if (value != null && strip) {
            value = value.strip();
        }

        if (value != null && cleanWhitespace) {
            value = value.replaceAll("\\s+", " ");
        }

        if (value != null && cleanNonAlphanumeric) {
            value = value.replaceAll("[^A-Za-z0-9\\sĘęĄąŚśŁłŻżŹźĆćŃńáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙäëïöüÄËÏÖÜâêîôûÂÊÎÔÛ]", "");
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

    public boolean isSet() {
        return value != null;
    }

    public boolean isNotSet() {
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

    @Nullable
    @Override
    public String serializeToString() {
        return this.value;
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
        StringValueTemplate<?> that = (StringValueTemplate<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}