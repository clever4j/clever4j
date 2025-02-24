package com.clever4j.collections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapUtil {

    public static Map<String, String> map() {
        return new HashMap<>();
    }

    public static String key(Object... values) {
        return Arrays.stream(values)
            .map(String::valueOf)
            .collect(Collectors.joining("-"));
    }

    // SELECT * FROM [training as t]
    // SELECT * FROM [training t]
    // UPDATE training
    public static String key(List<?> values) {
        return values.stream()
            .map(String::valueOf)
            .collect(Collectors.joining("-"));
    }

    // public static <K, R> R computeIfAbsent(Map<K, R> map, List<?> keys, Function<K, R> mappingFunction) {
    //     String key = keys.stream()
    //         .map(object -> {
    //
    //         });
    //
    //     return map.computeIfAbsent(keys)
    // }
}
