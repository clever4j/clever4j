package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.valuetype.serializable.StringSerializable;
import jakarta.annotation.Nullable;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@AllNonnullByDefault
public abstract class UuidValueTemplate<T extends UuidValueTemplate<T>> implements NullableValue, Comparable<T>, StringSerializable {

    private final static Comparator<UUID> STRING_COMPARATOR = Comparator.nullsLast(UUID::compareTo);

    @Nullable
    private final UUID value;

    protected UuidValueTemplate(@Nullable UUID value) {
        this.value = value;
    }

    @Nullable
    public static UUID getUuidFromString(@Nullable String uuid) {
        if (uuid == null) {
            return null;
        }

        return UUID.fromString(uuid);
    }

    protected abstract T createObject(@Nullable UUID value);

    @Nullable
    public UUID getValue() {
        return value;
    }

    @SuppressWarnings("unused")
    public UUID requireValue() {
        requireNonNull(value);
        return value;
    }

    public String requireString() {
        return requireNonNull(value).toString();
    }

    @Override
    public int compareTo(T value) {
        return STRING_COMPARATOR.compare(this.value, value.getValue());
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean equals(@Nullable UUID object) {
        return Objects.equals(value, object);
    }

    public boolean equals(String value) {
        return Objects.equals(this.value, UUID.fromString(value));
    }

    public boolean isSet() {
        return value != null;
    }

    public boolean isNotSet() {
        return value == null;
    }

    @Nullable
    @Override
    public String serializeToString() {
        return value == null ? null : value.toString();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UuidValueTemplate<?> that = (UuidValueTemplate<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}