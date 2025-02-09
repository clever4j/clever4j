package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public record Repository(
    String id,
    String dbUrl,
    String dbUser,
    String dbPassword,
    RecordGenerator recordGenerator
) {

}
