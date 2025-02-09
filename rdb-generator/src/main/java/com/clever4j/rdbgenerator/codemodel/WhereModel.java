package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;

import java.util.List;

@AllNonnullByDefault
public record WhereModel(
    String name,
    String packageName,
    String tableName,
    List<RecordFieldModel> fields
) {
}
