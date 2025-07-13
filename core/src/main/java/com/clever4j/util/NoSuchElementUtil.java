package com.clever4j.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public final class NoSuchElementUtil {

    private NoSuchElementUtil() {

    }

    @Nullable
    public static <T> T handleNoSuchElement(@Nonnull Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
