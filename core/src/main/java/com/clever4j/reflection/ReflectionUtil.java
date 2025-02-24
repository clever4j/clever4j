package com.clever4j.reflection;

import com.clever4j.lang.AllNonnullByDefault;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@AllNonnullByDefault
public final class ReflectionUtil {

    private ReflectionUtil() {
    }

    public static String getFieldName(Object obj, String field) {
        try {
            obj.getClass().getDeclaredField("value");

            return field;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(Object obj, String field) {
        try {
            Field objectField = obj.getClass().getDeclaredField(field);
            objectField.setAccessible(true);

            return objectField.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(Object obj, Field field) {
        try {
            field.setAccessible(true);

            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Field> getFields(Object obj, List<String> fields) {
        return getFields(obj.getClass(), fields);
    }

    public static List<Field> getFields(Class<?> type, List<String> fields) {
        return Arrays.stream(type.getDeclaredFields())
            .filter(field -> fields.contains(field.getName()))
            .toList();
    }

}
