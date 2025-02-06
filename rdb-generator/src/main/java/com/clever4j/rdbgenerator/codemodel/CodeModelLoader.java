package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.types.RecordFieldId;
import com.clever4j.rdbgenerator.configuration.Configuration.Config.Repository;
import jakarta.annotation.Nullable;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllNonnullByDefault
public final class CodeModelLoader implements Closeable {

    private final Repository repository;

    @Nullable
    private Connection connection;

    public CodeModelLoader(Repository repository) {
        this.repository = repository;
    }

    public CodeModel load() throws SQLException {
        Connection connection = getConnection();
        DatabaseMetaData metadata = connection.getMetaData();
        List<EntryCodeModel> entries = new ArrayList<>();
        TypeMapper typeMapper = new TypeMapper();
        ObjectNameProvider objectNameProvider = new ObjectNameProvider();

        ResultSet tableResultSet = metadata.getTables(connection.getCatalog(), null, "%", new String[]{"TABLE"});

        String excludeRegex = repository.getRecordGenerator().getExcludeRegex();

        while (tableResultSet.next()) {
            String tableName = tableResultSet.getString("TABLE_NAME");

            // todo
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
                repository.getRecordGenerator().getPackageName(),
                tableName,
                fields
            );

            // // Dao
            // DaoModel daoModel = new DaoModel(
            //     recordModel.name() + "-dao",
            //     objectNameProvider.getDaoName(recordModel),
            //     packageName,
            //     recordModel
            // );

            entries.add(new EntryCodeModel(recordModel, null, null));
        }

        // return new CodeModel(entries, new WhereOperatorModel("WhereOperator", packageName));

        return null;
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

    private Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(
                repository.getDbUrl(),
                repository.getDbUser(),
                repository.getDbPassword()
            );
        }

        return connection;
    }

    @Override
    public void close() {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
