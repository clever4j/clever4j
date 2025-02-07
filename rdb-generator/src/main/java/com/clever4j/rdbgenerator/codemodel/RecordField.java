package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public record RecordField(
    Class<?> type,
    String name,
    String getByInName,
    String tableName,
    String columnName,
    boolean primaryKey,
    boolean nullable
) {
}
