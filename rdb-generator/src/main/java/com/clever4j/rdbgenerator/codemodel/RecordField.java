package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.types.RecordFieldId;

@AllNonnullByDefault
public record RecordField(
    RecordFieldId recordFieldId,
    Class<?> type,
    String name,
    String getByInName,
    String tableName,
    String columnName,
    boolean primaryKey,
    boolean nullable
) {
}
