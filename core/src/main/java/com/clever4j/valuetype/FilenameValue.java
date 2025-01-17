package com.clever4j.valuetype;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllNonnullByDefault
public final class FilenameValue extends FilenameValueTemplate<FilenameValue> {

    private static final FilenameValue NULL_INSTANCE = new FilenameValue(null);
    private static final Map<String, FilenameValue> INSTANCES = new ConcurrentHashMap<>();

    private FilenameValue(@Nullable String value) {
        super(value, true);
    }

    public static FilenameValue of(@Nullable String value) {
        if (value == null) {
            return NULL_INSTANCE;
        } else {
            return INSTANCES.computeIfAbsent(value, FilenameValue::new);
        }
    }

    @Override
    protected FilenameValue createObject(@Nullable String value) {
        return of(value);
    }
}
