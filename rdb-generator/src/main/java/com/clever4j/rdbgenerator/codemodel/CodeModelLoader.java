package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.Column;
import com.clever4j.rdb.metadata.DataType;
import com.clever4j.rdb.metadata.Engine;
import com.clever4j.rdbgenerator.TypeMapper;
import com.clever4j.rdbgenerator.codemodel.types.RecordFieldId;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllNonnullByDefault
public final class CodeModelLoader {

    public CodeModel load(String packageName, Connection connection, TypeMapper typeMapper, ObjectNameProvider objectNameProvider) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();

        ResultSet tableResultSet = metadata.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});
        List<RecordModel> records = new ArrayList<>();

        while (tableResultSet.next()) {
            String tableName = tableResultSet.getString("TABLE_NAME");

            // fields
            List<RecordField> fields = new ArrayList<>();

            ResultSet columnResultSet = metadata.getColumns(null, null, tableName, null);

            Set<String> primaryKeys = getPrimaryKeys(tableName, metadata);

            while (columnResultSet.next()) {
                String columnName = columnResultSet.getString("COLUMN_NAME");
                String typeName = columnResultSet.getString("TYPE_NAME");

                boolean primaryKey = primaryKeys.contains(columnName);
                boolean nullable = columnResultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;

                fields.add(new RecordField(
                    RecordFieldId.of(tableName, columnName),
                    typeMapper.map(typeName),
                    objectNameProvider.getFieldName(columnName),
                    tableName,
                    columnName,
                    primaryKey,
                    nullable
                ));
            }

            records.add(new RecordModel(
                objectNameProvider.getRecordName(tableName),
                tableName,
                fields
            ));
        }

        // Daos
        List<DaoModel> doas = records.stream()
            .map(record -> {
                return new DaoModel(
                    record.name() + "-dao",
                    record.name() + "Dao",
                    record
                );
            }).toList();

        return new CodeModel(records, doas);
    }

    private List<Column> getColumns(String tableName, DatabaseMetaData metaData) throws SQLException {
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        List<Column> columns = new ArrayList<>();

        Set<String> primaryKeys = getPrimaryKeys(tableName, metaData);

        while (resultSet.next()) {
            String name = resultSet.getString("COLUMN_NAME");
            String type = resultSet.getString("TYPE_NAME");

            boolean primaryKey = primaryKeys.contains(name);
            boolean isNullable = resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;

            columns.add(new Column(
                name,
                primaryKey,
                new DataType(type, Engine.POSTGRESQL),
                isNullable
            ));
        }

        return columns;
    }

    private Set<String> getPrimaryKeys(String tableName, DatabaseMetaData metaData) throws SQLException {
        ResultSet resultSet = metaData.getPrimaryKeys(null, null, tableName);
        Set<String> primaryKeys = new HashSet<>();

        while (resultSet.next()) {
            String pkColumnName = resultSet.getString("COLUMN_NAME");
            primaryKeys.add(pkColumnName);
        }

        return primaryKeys;
    }

    private String mapDbTypeToJavaType(String columnType) {
        return switch (columnType) {
            case "uuid" -> "java.lang.String";
            case "timestamp" -> "java.time.LocalDateTime";
            case "varchar" -> "java.lang.String";
            case "text" -> "java.lang.String";
            case "bool" -> "java.lang.Boolean";
            case "int4" -> "java.lang.Integer";
            default -> "java.lang.String";
        };
    }
}
