package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

import java.util.Objects;

@AllNonnullByDefault
public abstract class BooleanValueTemplate<T extends BooleanValueTemplate<T>> {

    @Nullable
    private final Boolean value;

    protected BooleanValueTemplate(@Nullable Boolean value) {
        this.value = value;
    }

    protected abstract T createObject(@Nullable Boolean value);

    @Nullable
    public Boolean getValue() {
        return value;
    }

    public Boolean requireValue() {
        if (value == null) {
            throw new IllegalStateException("value is null");
        }

        return value;
    }

    public Boolean getValueOrDefault(Boolean defaultValue) {
        return this.value == null ? defaultValue : this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        BooleanValueTemplate<?> that = (BooleanValueTemplate<?>) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}