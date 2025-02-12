package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public record DaoModel(
    String name,
    String packageName,
    String simpleName,
    RecordModel record
) {
}
