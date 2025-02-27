package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.text.NamingStyleConverter;

@AllNonnullByDefault
public final class ObjectNameProvider {

    private static final NamingStyleConverter NAMING_STYLE_CONVERTER = new NamingStyleConverter();

    public String formatPascalCase(String input) {
        return NAMING_STYLE_CONVERTER.convert(input, NamingStyleConverter.NamingStyle.PASCAL_CASE);
    }

    public String getRecordName(String tableName) {
        return NAMING_STYLE_CONVERTER.convert(tableName, NamingStyleConverter.NamingStyle.PASCAL_CASE) + "Record";
    }

    public String getFieldName(String columnName) {
        return NAMING_STYLE_CONVERTER.convert(columnName, NamingStyleConverter.NamingStyle.CAMEL_CASE);
    }

    public String getByInName(String columnName) {
        return "getBy" + NAMING_STYLE_CONVERTER.convert(columnName, NamingStyleConverter.NamingStyle.PASCAL_CASE) + "In";
    }

    public String getTemplateDaoSimpleName(String tableName) {
        return NAMING_STYLE_CONVERTER.convert(tableName, NamingStyleConverter.NamingStyle.PASCAL_CASE) + "DaoTemplate";
    }

    public String getDaoSimpleName(String tableName) {
        return NAMING_STYLE_CONVERTER.convert(tableName, NamingStyleConverter.NamingStyle.PASCAL_CASE) + "Dao";
    }

    public String getImplementationDaoTemplateSimpleName(String tableName) {
        return NAMING_STYLE_CONVERTER.convert(tableName, NamingStyleConverter.NamingStyle.PASCAL_CASE) + "DaoImplTemplate";
    }

    public String getImplementationDaoSimpleName(String tableName) {
        return NAMING_STYLE_CONVERTER.convert(tableName, NamingStyleConverter.NamingStyle.PASCAL_CASE) + "DaoImpl";
    }
}