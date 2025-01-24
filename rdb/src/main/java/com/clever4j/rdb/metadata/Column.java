package com.clever4j.rdb.metadata;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public record Column(
    String name,
    boolean primaryKey,
    ColumnType type,
    boolean nullable
) {
}
