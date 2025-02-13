package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;

import java.nio.file.Path;

@AllNonnullByDefault
public record Repository(
    String id,
    String dbUrl,
    String dbUser,
    String dbPassword,

    String recordPackageName,
    Path recordOutput,

    String daoPackageName,
    Path daoOutput,

    String implementationDaoPackageName,
    Path implementationDaoOutput
) {

}
