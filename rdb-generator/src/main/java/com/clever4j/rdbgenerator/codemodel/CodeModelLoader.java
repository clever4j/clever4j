package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.ColumnMetadata;
import com.clever4j.rdb.metadata.DatabaseMetadata;
import com.clever4j.rdb.metadata.TableMetadata;
import com.clever4j.rdbgenerator.configuration.Repository;
import jakarta.annotation.Nullable;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllNonnullByDefault
public final class CodeModelLoader implements Closeable {

    private final Repository repository;
    private final DatabaseMetadata databaseMetadata;

    @Nullable
    private Connection connection;

    public CodeModelLoader(Repository repository, DatabaseMetadata databaseMetadata) {
        this.repository = repository;
        this.databaseMetadata = databaseMetadata;
    }

    public CodeModel load() throws SQLException {
        Connection connection = getConnection();
        DatabaseMetaData metadata = connection.getMetaData();
        TypeMapper typeMapper = new TypeMapper();
        ObjectNameProvider objectNameProvider = new ObjectNameProvider();
        List<RecordModel> records = new ArrayList<>();
        List<DaoModel> daos = new ArrayList<>();

        for (TableMetadata table : databaseMetadata.tables()) {
            if (!table.name().equals("test_tag")) {
                continue;
            }

            if (isTableExcluded(table.name())) {
                continue;
            }

            // fields --------------------------------------------------------------------------------------------------
            List<RecordFieldModel> fields = new ArrayList<>();

            for (ColumnMetadata column : table.columns()) {
                fields.add(new RecordFieldModel(
                    typeMapper.map(column.type()),
                    objectNameProvider.getFieldName(column.name()),
                    objectNameProvider.getByInName(column.name()),
                    table.name(),
                    column.name(),
                    column.primaryKey(),
                    column.nullable()
                ));
            }

            // record --------------------------------------------------------------------------------------------------
            String recordPackageName = repository.recordGenerator().packageName();
            String recordSimpleName = objectNameProvider.getRecordName(table.name());
            String recordName = recordPackageName + "." + recordSimpleName;

            RecordModel record = new RecordModel(
                recordName,
                recordSimpleName,
                recordPackageName,
                table,
                fields
            );

            records.add(record);

            // // Dao
            // DaoModel daoModel = new DaoModel(
            //     record.name() + "-dao",
            //     objectNameProvider.getDaoName(record),
            //     packageName,
            //     record
            // );
        }

        return new CodeModel(records, daos);
    }

    private boolean isTableExcluded(String tableName) {
        // if (repository.getRecordGenerator().getExcludeRegex()) {

        // }

        System.out.printf("test");

        return false;
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
                repository.dbUrl(),
                repository.dbUser(),
                repository.dbPassword()
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
