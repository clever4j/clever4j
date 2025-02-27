package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.TableMetadata;
import com.clever4j.rdbgenerator.configuration.Database;

import java.nio.file.Path;
import java.util.List;

@AllNonnullByDefault
public record RecordModel(
    String name,
    String simpleName,
    String packageName,
    Path output,
    TableMetadata table,
    List<RecordFieldModel> fields,
    List<RecordFieldModel> primaryKeys,
    Database database
) {
}