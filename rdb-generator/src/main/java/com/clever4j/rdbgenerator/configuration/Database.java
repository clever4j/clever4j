package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;

import java.nio.file.Path;
import java.util.List;

@AllNonnullByDefault
public record Database(
    String id,
    String dbUrl,
    String dbUser,
    String dbPassword,

    List<String> excludeTables,

    String packageName,
    Path output,

    List<String> recordAnnotations,
    List<String> daoAnnotations,
    List<String> baseImplementationDaoAnnotations,
    List<String> implementationDaoAnnotations
) {

}
