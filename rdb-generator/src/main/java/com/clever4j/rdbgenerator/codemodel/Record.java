package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.TableMetadata;

import java.util.List;

@AllNonnullByDefault
public record Record(
    String name,
    String simpleName,
    String packageName,
    TableMetadata table,
    List<RecordField> fields
) {
}
