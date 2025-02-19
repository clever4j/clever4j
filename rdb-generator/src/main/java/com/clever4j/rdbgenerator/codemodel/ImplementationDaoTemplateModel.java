package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.configuration.Database;

import java.nio.file.Path;

@AllNonnullByDefault
public record ImplementationDaoTemplateModel(
    String name,
    String packageName,
    Path output,
    String simpleName,
    Database database,
    RecordModel recordModel,
    DaoTemplateModel daoTemplateModel,
    DaoModel daoModel
) {
}
