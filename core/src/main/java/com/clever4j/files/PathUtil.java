package com.clever4j.files;

import com.clever4j.lang.AllNonnullByDefault;

import java.nio.file.Path;

@AllNonnullByDefault
public final class PathUtil {

    private PathUtil() {
    }

    public static String getExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public static Path setExtension(Path path, String newExtension) {
        String fileName = getName(path);
        return path.resolveSibling(fileName + "." + newExtension);
    }

    public static String getName(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    public static Path setName(Path path, String newName) {
        String extension = getExtension(path);
        return path.resolveSibling(newName + (extension.isEmpty() ? "" : "." + extension));
    }
}
