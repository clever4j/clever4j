package com.clever4j.collections;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

@AllNonnullByDefault
public class ListUtil {

    private ListUtil() {

    }

    public static <T> void moveElement(List<T> list, int fromIndex, int toIndex) {
        T remove = list.remove(fromIndex);
        list.add(toIndex, remove);
    }

    // get -------------------------------------------------------------------------------------------------------------
    public static <T> T requireGet(List<T> values, Predicate<T> test) {
        T result = get(values, test);

        if (result == null) {
            throw new NoSuchElementException("Element not found");
        }

        return result;
    }

    @Nullable
    public static <T> T get(List<T> values, Predicate<T> test) {
        for (T t : values) {
            if (test.test(t)) {
                return t;
            }
        }

        return null;
    }

    public static <T> boolean anyMatch(List<T> values, Predicate<T> test) {
        for (T t : values) {
            if (test.test(t)) {
                return true;
            }
        }

        return false;
    }

    // list ------------------------------------------------------------------------------------------------------------

    public static <T> List<T> list() {
        return new ArrayList<>();
    }

    public static <T> List<T> list(T... values) {
        return List.of(values);
    }

    public static <T> List<T> list(List<T> values) {
        return new ArrayList<>(values);
    }

    public static <T> void add(List<T> input, T... values) {
        input.addAll(Arrays.asList(values));
    }

    @Nonnull
    public static <T> Integer getIndex(List<T> values, Predicate<T> test) {
        for (int i = 0; i < values.size(); i++) {
            if (test.test(values.get(i))) {
                return i;
            }
        }

        throw new NoSuchElementException("Element not found");
    }

    public static <T> void move(List<T> values, Predicate<T> test, int targetIndex) {
        int index = getIndex(values, test);

        T value = values.remove(index);

        values.add(targetIndex, value);
    }

    public static <T> void clearAndAdd(List<T> values, List<T> add) {
        values.clear();
        values.addAll(add);
    }

    @Nullable
    public static <T> Integer findIndex(List<T> values, Predicate<T> test) {
        try {
            return getIndex(values, test);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
