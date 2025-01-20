package com.clever4j.rdb.metadata;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public record Column(
    String name,
    String type,
    boolean nullable
) {
}
