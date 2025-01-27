package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;

import java.util.List;

@AllNonnullByDefault
public record RecordModel(
    String packageName,
    String name,
    String tableName,
    List<RecordField> fields
) {
}
