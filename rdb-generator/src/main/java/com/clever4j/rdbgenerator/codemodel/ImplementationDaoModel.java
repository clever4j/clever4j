package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.configuration.Database;

@AllNonnullByDefault
public record ImplementationDaoModel(
    String name,
    String packageName,
    String simpleName,
    DaoModel daoModel,
    RecordModel record,
    Database database
) {
}
