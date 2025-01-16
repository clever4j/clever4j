package com.clever4j.lang;

import com.clever4j.io.InputStreamUtil;
import jakarta.annotation.Nullable;

import java.io.InputStream;

@AllNonnullByDefault
public final class ResourceUtil {

    @Nullable
    public static InputStream getInputStream(String path) {
        return ResourceUtil.class.getClassLoader().getResourceAsStream(path);
    }

    @Nullable
    public static byte[] getByteArray(String path) {
        InputStream stream = ResourceUtil.class.getClassLoader().getResourceAsStream(path);

        if (stream == null) {
            return null;
        }

        return InputStreamUtil.toByteArray(stream);
    }
}
