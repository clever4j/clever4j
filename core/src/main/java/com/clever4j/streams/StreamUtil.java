package com.clever4j.streams;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

@AllNonnullByDefault
public class StreamUtil {

    @Nonnull
    public static <R, T> List<R> map(List<T> input, Function<? super T, ? extends R> mapper) {
        List<R> result = new ArrayList<>();

        for (T t : input) {
            result.add(mapper.apply(t));
        }

        return result;
    }

    @Nonnull
    public static <R, U, T> List<R> map(List<T> input, BiFunction<? super T, Integer, ? extends R> mapper) {
        List<R> result = new ArrayList<>();
        int i = 0;

        for (T t : input) {
            result.add(mapper.apply(t, i));
            i++;
        }

        return result;
    }

    @Nonnull
    public static <R, U, T> List<R> map(List<T> input, Predicate<? super T> filter, BiFunction<? super T, Integer, ? extends R> mapper) {
        List<R> result = new ArrayList<>();

        int i = 0;

        for (T t : input) {
            if (filter.test(t)) {
                result.add(mapper.apply(t, i));
                i++;
            }
        }

        return result;
    }

    @Nullable
    public static <T> T find(List<T> input, Predicate<T> predicate) {
        for (T t : input) {
            if (predicate.test(t)) {
                return t;
            }
        }

        return null;
    }

    @Nonnull
    public static <T> T get(List<T> input, Predicate<T> predicate) {
        for (T t : input) {
            if (predicate.test(t)) {
                return t;
            }
        }

        throw new IllegalStateException("Element not found");
    }

    public static <T> int countIf(List<T> input, Predicate<T> predicate) {
        int counter = 0;

        for (T t : input) {
            if (predicate.test(t)) {
                counter++;
            }
        }

        return counter;
    }

    public static <T> List<T> filter(List<T> input, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();

        for (T t : input) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }

        return result;
    }

    public static <T, R> List<R> filter(List<T> input, Predicate<T> predicate, Function<T, R> mapper) {
        List<T> result = new ArrayList<>();

        for (T t : input) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }

        return map(result, mapper);
    }

    public static <T> T findFirst(List<T> input, Predicate<T> test) {
        return input.stream()
            .filter(test)
            .findFirst()
            .orElseThrow();
    }

    @Nullable
    public static <T> T findFirst(List<T> input, Predicate<T> test, @Nullable T orElse) {
        return input.stream()
            .filter(test)
            .findFirst()
            .orElse(orElse);
    }

    public static <T> boolean anyMatch(List<T> input, Predicate<T> test) {
        return input.stream()
            .anyMatch(test);
    }

    public static <T> boolean noneMatch(List<T> input, Predicate<T> test) {
        return input.stream()
            .noneMatch(test);
    }
}
