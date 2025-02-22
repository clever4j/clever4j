package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public record RecordFieldModel(
    Class<?> type,
    String canonicalNameType,
    String name,
    String pascalName,
    String getByInName,
    String tableName,
    String columnName,
    boolean primaryKey,
    boolean nullable
) {
}
