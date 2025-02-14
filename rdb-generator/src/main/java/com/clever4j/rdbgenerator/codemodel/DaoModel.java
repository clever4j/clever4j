package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.configuration.Database;

@AllNonnullByDefault
public record DaoModel(
    String name,
    String packageName,
    String simpleName,
    RecordModel record,
    Database database
) {
}
