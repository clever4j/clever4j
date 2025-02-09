package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;

import java.nio.file.Path;

@AllNonnullByDefault
public record DaoGenerator(
    String packageName,
    Path output
) {
}