package com.clever4j.fs;

import com.clever4j.lang.AllNonnullByDefault;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllNonnullByDefault
public final class FsHandler {

    public static void create(String path) {
        Path path1 = Path.of(path);

        if (!path1.isAbsolute()) {
            throw new IllegalArgumentException("FsHandler only supports absolute paths");
        }

        if (!Files.exists(path1)) {

        }

        if (!Files.isDirectory(path1)) {
            throw new IllegalArgumentException("FsHandler only supports directory paths");
        }
    }

//    private final Path fileSystemPath;
//    private final PathTokenizer pathTokenizer = new PathTokenizer("/");
//
//    // constructor -----------------------------------------------------------------------------------------------------
//    public FsHandler(Path path) {
//        this.fileSystemPath = path;
//    }
//
//    public FsHandler(String parent, String child) {
//        this.fileSystemPath = Path.of(parent, child);
//    }
//
//    public static FsHandler create(Path path) {
//        if (Files.notExists(path)) {
//            try {
//                Files.createDirectories(path);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        return new FsHandler(path);
//    }
//
//    public static FsHandler create(Path path, boolean delete) {
//        if (Files.exists(path) && delete) {
//            FilesUtil.delete(path);
//        }
//
//        return create(path);
//    }
//
//    // list ------------------------------------------------------------------------------------------------------------
//    public FileSystemObjects list() {
//        return list(Path.of(""));
//    }
//
//    public FileSystemObjects list(boolean recursive) {
//        return list(Path.of(""), recursive);
//    }
//
//    public FileSystemObjects list(Predicate<FsObject> test, boolean recursive) {
//        try (Stream<Path> stream = Files.walk(fileSystemPath)) {
//            List<FsObject> fsObjects = stream
//                .map(this::createFileSystemObject)
//                .filter(fsObject -> !fsObject.getPath().toString().isEmpty())
//                .filter(test)
//                .toList();
//
//            return new FileSystemObjects(fsObjects);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public FileSystemObjects list(Path path) {
//        Path resolvePath = resolve(path);
//
//        if (!Files.isDirectory(resolvePath)) {
//            throw new IllegalArgumentException("Cannot list non directory: " + path);
//        }
//
//        try (Stream<Path> stream = Files.list(resolvePath)) {
//            List<FsObject> fsObjects = stream
//                .map(this::createFileSystemObject)
//                .toList();
//
//            return new FileSystemObjects(fsObjects);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public FileSystemObjects list(Path path, boolean recursive) {
//        Path resolvePath = resolve(path);
//
//        if (!Files.isDirectory(resolvePath)) {
//            throw new IllegalArgumentException("Cannot list non directory: " + path);
//        }
//
//        try (Stream<Path> stream = Files.walk(resolvePath)) {
//            List<FsObject> fsObjects = stream
//                .skip(1)
//                .map(this::createFileSystemObject)
//                .toList();
//
//            return new FileSystemObjects(fsObjects);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // // info ------------------------------------------------------------------------------------------------------------
//    // @Nullable
//    // public FsObject info(Path path) {
//    //     if (!exists(path)) {
//    //         return null;
//    //     }
//
//    //     return createFileSystemObject(resolve(path));
//    // }
//
//    // // -----------------------------------------------------------------------------------------------------------------
//    // private FsObject createFileSystemObject(Path path) {
//    //     boolean directory = Files.isDirectory(path);
//    //     boolean regularFile = Files.isRegularFile(path);
//    //     boolean symbolicLink = Files.isSymbolicLink(path);
//
//    //     path = relativize(path);
//
//    //     PathTokenizer.PathParts pathParts = pathTokenizer.getPathParts(path.toString());
//
//    //     Path parent = path.getParent();
//
//    //     if (parent == null) {
//    //         parent = Path.of("");
//    //     }
//
//    //     return new FsObject(
//    //         path,
//    //         parent,
//    //         pathParts.name(),
//    //         pathParts.extension(),
//    //         pathParts.fileName(),
//    //         directory,
//    //         regularFile,
//    //         symbolicLink
//    //     );
//    // }
//
//    // getInputStream --------------------------------------------------------------------------------------------------
//    public InputStream getInputStream(Path path) {
//        try {
//            return Files.newInputStream(resolve(path));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // public ----------------------------------------------------------------------------------------------------------
//    public boolean exists(Path path) {
//        return Files.exists(resolve(path));
//    }
//
//    public boolean exists(String path) {
//        return exists(Path.of(path));
//    }
//
//    public boolean exists() {
//        return Files.exists(fileSystemPath);
//    }
//
//    public void mkdirs() {
//        if (Files.exists(fileSystemPath)) {
//            return;
//        }
//
//        try {
//            Files.createDirectories(fileSystemPath);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void mkdirs(Path path) {
//        path = resolve(path);
//
//        if (Files.exists(path)) {
//            return;
//        }
//
//        try {
//            Files.createDirectories(path);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // resolve ---------------------------------------------------------------------------------------------------------
//    public Path resolve(String path) {
//        return this.fileSystemPath.resolve(Path.of(path));
//    }
//
//    public Path resolve(Path path) {
//        return this.fileSystemPath.resolve(path);
//    }
//
//    // relativize ------------------------------------------------------------------------------------------------------
//    public Path relativize(Path path) {
//        return this.fileSystemPath.relativize(path);
//    }
//
//    public Path getFileSystemPath() {
//        return fileSystemPath;
//    }
//
//    public String getPathString() {
//        return fileSystemPath.toString();
//    }
//
//    // copy ------------------------------------------------------------------------------------------------------------
//    public void copy(InputStream inputStream, Path target) {
//        try {
//            if (target.getParent() != null) {
//                this.mkdirs(target.getParent());
//            }
//
//            Files.copy(inputStream, resolve(target));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // ls --------------------------------------------------------------------------------------------------------------
//    public List<Path> ls() {
//        try (Stream<Path> stream = Files.find(fileSystemPath, 1, (path, basicFileAttributes) -> true)) {
//            return stream
//                .map(fileSystemPath::relativize)
//                .filter(path1 -> !path1.toString().isEmpty())
//                .collect(Collectors.toCollection(ArrayList::new));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // getFiles --------------------------------------------------------------------------------------------------------
//    public List<Path> getFiles() {
//        try (Stream<Path> stream = Files.walk(fileSystemPath)) {
//            return stream
//                .map(fileSystemPath::relativize)
//                .filter(path1 -> !path1.toString().isEmpty())
//                .collect(Collectors.toCollection(ArrayList::new));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<Path> getFiles(Predicate<Path> test) {
//        return getFiles().stream()
//            .filter(test)
//            .collect(Collectors.toCollection(ArrayList::new));
//    }
//
//    public List<Path> getFiles(String startsWith, String contains, String endsWith, String extension) {
//        return getFiles().stream()
//            .filter(path1 -> {
//                String pathString = path1.toString();
//
//                boolean startsWithFlag = startsWith == null || pathString.startsWith(startsWith);
//                boolean containsFlag = contains == null || pathString.contains(contains);
//                boolean endsWithFlag = endsWith == null || pathString.endsWith(endsWith);
//                boolean extensionFlag = extension == null || pathString.endsWith("." + extension);
//
//                return startsWithFlag && containsFlag && endsWithFlag && extensionFlag;
//            })
//            .collect(Collectors.toCollection(ArrayList::new));
//    }
//
//    public void readLine(Path path, Function<String, Boolean> consumer) {
//        FilesUtil.readLine(resolve(path), consumer);
//    }
//
//    public void move(Path file, Path target) {
//        try {
//            Files.move(resolve(file), resolve(target));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void delete(Path path) {
//        if (!exists(path)) {
//            return;
//        }
//
//        FilesUtil.delete(resolve(path));
//    }
//
//    public void delete() {
//        FilesUtil.delete(resolve(fileSystemPath));
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//    // FileSystemObject
//    // -----------------------------------------------------------------------------------------------------------------
//    public class FsObject {
//
//        private final Path path;
//        private final Path parent;
//        private final String name;
//        private final String extension;
//        private final String fileName;
//
//        private final boolean directory;
//        private final boolean regularFile;
//        private final boolean symbolicLink;
//
//        public FsObject(Path path, Path parent, String name, String extension, String fileName, boolean directory, boolean regularFile, boolean symbolicLink) {
//            this.path = path;
//            this.parent = parent;
//            this.name = name;
//            this.extension = extension;
//            this.fileName = fileName;
//            this.directory = directory;
//            this.regularFile = regularFile;
//            this.symbolicLink = symbolicLink;
//        }
//
//        public long size() {
//            if (!isRegularFile()) {
//                return 0;
//            }
//
//            try {
//                return Files.size(resolve(path));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        public boolean isEmpty() {
//            if (isDirectory()) {
//                throw new NotImplementedException();
//            } else {
//                return size() == 0;
//            }
//        }
//
//        public Path getParent() {
//            return parent;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getExtension() {
//            return extension;
//        }
//
//        public String getFileName() {
//            return fileName;
//        }
//
//        public Path getPath() {
//            return path;
//        }
//
//        public Path getAbsolutePath() {
//            return resolve(this.path).toAbsolutePath();
//        }
//
//        public InputStream getInputStream() {
//            return FsHandler.this.getInputStream(path);
//        }
//
//        public boolean isDirectory() {
//            return directory;
//        }
//
//        public boolean isRegularFile() {
//            return regularFile;
//        }
//
//        public boolean isSymbolicLink() {
//            return symbolicLink;
//        }
//
//        // list --------------------------------------------------------------------------------------------------------
//        public FileSystemObjects list() {
//            if (directory) {
//                return FsHandler.this.list(path);
//            } else {
//                return new FileSystemObjects(List.of());
//            }
//        }
//
//        public FileSystemObjects list(boolean recursive) {
//            if (directory) {
//                return FsHandler.this.list(path, recursive);
//            } else {
//                return new FileSystemObjects(List.of());
//            }
//        }
//
//        // info --------------------------------------------------------------------------------------------------------
//        @Nullable
//        public FsObject info(Path path) {
//            if (directory) {
//                return FsHandler.this.info(this.path.resolve(path));
//            } else {
//                return null;
//            }
//        }
//
//        // copy --------------------------------------------------------------------------------------------------------
//        public void copy(Path target) {
//            if (target.isAbsolute()) {
//                FsUtil.copy(resolve(path), target);
//            } else {
//                FsUtil.copy(resolve(path), resolve(target));
//            }
//        }
//
//        // move --------------------------------------------------------------------------------------------------------
//        public void move(Path target) {
//            if (target.isAbsolute()) {
//                FsUtil.move(resolve(path), target);
//            } else {
//                FsUtil.move(resolve(path), resolve(target));
//            }
//        }
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//    // FileSystemObjects
//    // -----------------------------------------------------------------------------------------------------------------
//    public class FileSystemObjects implements Iterable<FsObject> {
//
//        private final List<FsObject> objects;
//
//        public FileSystemObjects(List<FsObject> objects) {
//            this.objects = objects;
//        }
//
//        // list --------------------------------------------------------------------------------------------------------
//        public List<FsObject> list() {
//            return objects.stream()
//                .filter(object -> object.getParent().toString().isEmpty())
//                .toList();
//        }
//
//        public List<FsObject> list(boolean recursive) {
//            return Collections.unmodifiableList(this.objects);
//        }
//
//        @Override
//        public Iterator<FsObject> iterator() {
//            return objects.iterator();
//        }
//
//        public Stream<FsObject> stream() {
//            return objects.stream();
//        }
//
//        public boolean isEmpty() {
//            return objects.isEmpty();
//        }
//    }
}
