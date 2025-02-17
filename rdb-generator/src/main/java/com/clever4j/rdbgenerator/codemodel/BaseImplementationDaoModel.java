package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.configuration.Database;

@AllNonnullByDefault
public record BaseImplementationDaoModel(
    String name,
    String packageName,
    String simpleName,
    Database database,
    RecordModel recordModel,
    BaseDaoModel baseDaoModel,
    DaoModel daoModel
) {
}
