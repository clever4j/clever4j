package com.clever4j.lang;

import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

@AllNonnullByDefault
public class LangUtil {

    @Nullable
    public static <T, R> R build(@Nullable T input, Factory<T, R> factory) {
        if (input == null) {
            return null;
        } else {
            return factory.create(input);
        }
    }

    public static <T, R> List<R> build(List<T> input, Factory<T, R> factory) {
        return input.stream().map(factory::create).toList();
    }

    // nonEmpty --------------------------------------------------------------------------------------------------------
    public static String nonEmptyOrDefault(@Nullable String value, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        } else {
            return value;
        }
    }

    // require ---------------------------------------------------------------------------------------------------------
    public static <T> T require(@Nullable T value) {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        } else {
            return value;
        }
    }

    public static <T> T require(@Nullable T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    public static <T> T require(@Nullable T value, String message) {
        if (value == null) {
            throw new NoSuchElementException(message);
        } else {
            return value;
        }
    }

    public static <T> T require(@Nullable T value, Supplier<RuntimeException> exceptionSupplier) {
        if (value == null) {
            throw exceptionSupplier.get();
        } else {
            return value;
        }
    }

    // todo replace with require
    // nonNull ---------------------------------------------------------------------------------------------------------
    public static <T> T nonNull(@Nullable T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null.");
        }

        return value;
    }

    public static String nonNull(@Nullable String value) {
        return value == null ? "" : value;
    }

    public static <T> List<T> nonNull(@Nullable List<T> value) {
        return value == null ? new ArrayList<>() : value;
    }

    public interface Factory<T, R> {
        R create(T input);
    }
}
