package com.clever4j.rdb.metadata;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public record ColumnType(
    String type,
    Engine engine
) {
}