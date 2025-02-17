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

    String recordPackageName,
    Path recordOutput,
    List<String> recordAnnotations,

    String baseDaoPackageName,
    Path baseDaoOutput,

    String daoPackageName,
    Path daoOutput,
    List<String> daoAnnotations,

    String baseImplementationDaoPackageName,
    Path baseImplementationDaoOutput,
    List<String> baseImplementationDaoAnnotations,

    String implementationDaoPackageName,
    Path implementationDaoOutput,
    List<String> implementationDaoAnnotations
) {

}
