package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.text.NamingStyleConverter;

@AllNonnullByDefault
public final class ObjectNameProvider {

    private static final NamingStyleConverter NAMING_STYLE_CONVERTER = new NamingStyleConverter();

    public String getRecordName(String tableName) {
        return NAMING_STYLE_CONVERTER.convert(tableName, NamingStyleConverter.NamingStyle.PASCAL_CASE) + "Record";
    }

    public String getFieldName(String columnName) {
        return NAMING_STYLE_CONVERTER.convert(columnName, NamingStyleConverter.NamingStyle.CAMEL_CASE);
    }

    public String getDaoName(RecordModel record) {
        return record.name() + "Dao";
    }
}
