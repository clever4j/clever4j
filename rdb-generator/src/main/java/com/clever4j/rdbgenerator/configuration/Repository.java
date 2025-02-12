package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

@AllNonnullByDefault
public record Repository(
    String id,
    String dbUrl,
    String dbUser,
    String dbPassword,
    RecordGenerator recordGenerator,

    @Nullable
    DaoGenerator daoGenerator,

    @Nullable
    ImplementationDaoGenerator implementationDaoGenerator
) {

}
