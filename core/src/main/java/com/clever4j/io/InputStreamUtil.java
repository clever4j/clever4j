package com.clever4j.io;

import com.clever4j.lang.AllNonnullByDefault;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@AllNonnullByDefault
public final class InputStreamUtil {

    public static byte[] toByteArray(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
