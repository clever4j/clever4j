package com.clever4j.files;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PathUtilTest {

    @Test
    void getExtension() {
        Path path = Path.of("./foo/bar.png");

        assertEquals("png", PathUtil.getExtension(path));
    }

    @Test
    void setExtension() {
        Path path = Path.of("./foo/bar.png");

        assertEquals(Path.of("./foo/bar.zip"), PathUtil.setExtension(path, "zip"));
    }

    @Test
    void getName() {
        Path path = Path.of("./foo/bar.png");

        assertEquals("bar", PathUtil.getName(path));
    }

    @Test
    void setName() {
        Path path = Path.of("./foo/bar.png");

        assertEquals(Path.of("./foo/foo.png"), PathUtil.setName(path, "foo"));
    }
}