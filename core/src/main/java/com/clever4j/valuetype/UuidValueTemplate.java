package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.valuetype.serializable.StringSerializable;
import jakarta.annotation.Nullable;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@AllNonnullByDefault
public abstract class UuidValueTemplate<T extends UuidValueTemplate<T>> implements Comparable<T>, StringSerializable {

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

    @Override
    public int compareTo(T value) {
        return STRING_COMPARATOR.compare(this.value, value.getValue());
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean equals(UUID object) {
        return Objects.equals(value, object);
    }

    @Nullable
    @Override
    public String serializeToString() {
        return value == null ? null : value.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UuidValueTemplate<?> that = (UuidValueTemplate<?>) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}