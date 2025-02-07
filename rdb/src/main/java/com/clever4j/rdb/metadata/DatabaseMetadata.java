package com.clever4j.rdb.metadata;

import com.clever4j.lang.AllNonnullByDefault;

import java.util.List;

@AllNonnullByDefault
public record DatabaseMetadata(
    List<TableMetadata> tables
) {

}
