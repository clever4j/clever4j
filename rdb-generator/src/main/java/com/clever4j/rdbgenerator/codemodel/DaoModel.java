package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.configuration.Database;

import java.nio.file.Path;

@AllNonnullByDefault
public record DaoModel(
    String name,
    String packageName,
    Path output,
    String simpleName,
    RecordModel recordModel,
    TemplateDaoModel templateDaoModel,
    Database database
) {
}
