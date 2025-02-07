package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public record DaoModel(
    String id,
    String name,
    String packageName,
    Record record
) {
}
