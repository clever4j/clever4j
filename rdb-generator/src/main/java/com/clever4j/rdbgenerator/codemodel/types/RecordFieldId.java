package com.clever4j.rdbgenerator.codemodel.types;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public class RecordFieldId {

    private final String tableName;
    private final String columnName;

    private RecordFieldId(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public static RecordFieldId of(String table, String name) {
        return new RecordFieldId(table, name);
    }
}