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
        List<EntryCodeModel> entries = new ArrayList<>();

        ResultSet tableResultSet = metadata.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});

        while (tableResultSet.next()) {
            String tableName = tableResultSet.getString("TABLE_NAME");

            if (!tableName.equals("storage_object")) {
                continue;
            }

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
                    objectNameProvider.getByInName(columnName),
                    tableName,
                    columnName,
                    primaryKey,
                    nullable
                ));
            }

            // Record
            RecordModel recordModel = new RecordModel(
                objectNameProvider.getRecordName(tableName),
                packageName,
                tableName,
                fields
            );

            // Where
            WhereModel whereModel = new WhereModel(
                objectNameProvider.getWhereName(recordModel),
                packageName,
                tableName,
                fields
            );

            // Dao
            DaoModel daoModel = new DaoModel(
                recordModel.name() + "-dao",
                objectNameProvider.getDaoName(recordModel),
                packageName,
                recordModel
            );

            entries.add(new EntryCodeModel(recordModel, whereModel, daoModel));
        }

        return new CodeModel(entries, new WhereOperatorModel("WhereOperator", packageName));
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
}
