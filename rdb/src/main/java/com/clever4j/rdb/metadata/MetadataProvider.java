package com.clever4j.rdb.metadata;

import com.clever4j.lang.AllNonnullByDefault;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllNonnullByDefault
public final class MetadataProvider {

    private final Connection connection;

    public MetadataProvider(Connection connection) {
        this.connection = connection;
    }

    public List<Table> getTables() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet resultSet = metaData.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});
        List<Table> tables = new ArrayList<>();

        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");
            List<Column> columns = getColumns(tableName, metaData);

            tables.add(new Table(tableName, Collections.unmodifiableList(columns)));
        }

        return tables;
    }

    private List<Column> getColumns(String tableName, DatabaseMetaData metaData) throws SQLException {
        ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
        List<Column> columns = new ArrayList<>();

        Set<String> primaryKeys = getPrimaryKeys(tableName, metaData);

        while (resultSet.next()) {
            String columnName = resultSet.getString("COLUMN_NAME");
            String columnType = resultSet.getString("TYPE_NAME");

            boolean primaryKey = primaryKeys.contains(columnName);
            boolean isNullable = resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;

            columns.add(new Column(columnName, primaryKey, columnType, isNullable));
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
}
