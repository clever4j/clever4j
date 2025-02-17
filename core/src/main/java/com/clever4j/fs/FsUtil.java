package com.clever4j.fs;

import com.clever4j.lang.AllNonnullByDefault;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

@AllNonnullByDefault
public final class FsUtil {

    private FsUtil() {

    }

    public static void createDirectory(String path) {
        createDirectory(Path.of(path));
    }

    public static void createDirectory(Path path, FileAttribute<?>... attrs) {
        try {
            Files.createDirectories(path, attrs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // exists ----------------------------------------------------------------------------------------------------------
    public static boolean exists(String path, LinkOption... options) {
        return exists(Path.of(path), options);
    }

    public static boolean exists(Path path, LinkOption... options) {
        return Files.exists(path, options);
    }

    // public static double getSize(Path path) throws IOException {
    //     return getSize(path, Unit.BYTES);
    // }

    // public static double getSize(Path path, Unit unit) throws IOException {
    //     long sizeInBytes = Files.isDirectory(path)
    //         ? Files.walk(path)
    //         .filter(Files::isRegularFile)
    //         .mapToLong(p -> {
    //             try {
    //                 return Files.size(p);
    //             } catch (IOException e) {
    //                 throw new RuntimeException("Błąd odczytu rozmiaru pliku", e);
    //             }
    //         })
    //         .sum()
    //         : Files.size(path);

    //     return switch (unit) {
    //         case MB -> sizeInBytes / (1024.0 * 1024.0);
    //         case GB -> sizeInBytes / (1024.0 * 1024.0 * 1024.0);
    //         default -> (double) sizeInBytes; // Domyślnie w bajtach
    //     };
    // }

    // public static void copy(Path path, Path target) {
    //     try {
    //         if (Files.isDirectory(path)) {
    //             Files.walkFileTree(path, new SimpleFileVisitor<>() {
    //                 @Override
    //                 public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
    //                     Path targetDir = target.resolve(path.relativize(dir));
    //                     Files.createDirectories(targetDir);
    //                     return FileVisitResult.CONTINUE;
    //                 }

    //                 @Override
    //                 public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    //                     Files.copy(file, target.resolve(path.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
    //                     return FileVisitResult.CONTINUE;
    //                 }
    //             });
    //         } else {
    //             Path parent = target.getParent();

    //             if (parent != null && !parent.toString().isEmpty()) {
    //                 Files.createDirectories(parent);
    //             }

    //             Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
    //         }
    //     } catch (Exception e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    // public static void move(Path path, Path target) {
    //     try {
    //         Files.move(path, target, StandardCopyOption.REPLACE_EXISTING);
    //     } catch (IOException exception) {
    //         throw new RuntimeException(exception);
    //     }
    // }

    // public static void delete(Path path) throws IOException {
    //     if (Files.isDirectory(path)) {
    //         Files.walkFileTree(path, new SimpleFileVisitor<>() {
    //             @Override
    //             public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
    //                 Files.delete(file);
    //                 return FileVisitResult.CONTINUE;
    //             }

    //             @Override
    //             public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
    //                 Files.delete(dir);
    //                 return FileVisitResult.CONTINUE;
    //             }
    //         });
    //     } else {
    //         Files.deleteIfExists(path);
    //     }
    // }

    // public static void touch(Path path) throws IOException {
    //     if (Files.exists(path)) {
    //         Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
    //     } else {
    //         Files.createFile(path);
    //     }
    // }

    // public enum Unit {
    //     BYTES,
    //     MB,
    //     GB
    // }
}
