package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.ColumnMetadata;
import com.clever4j.rdb.metadata.DatabaseMetadata;
import com.clever4j.rdb.metadata.TableMetadata;
import com.clever4j.rdbgenerator.configuration.DaoGenerator;
import com.clever4j.rdbgenerator.configuration.Repository;
import jakarta.annotation.Nullable;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;

@AllNonnullByDefault
public final class CodeModelLoader {

    private final Repository repository;
    private final DatabaseMetadata databaseMetadata;

    public CodeModelLoader(Repository repository, DatabaseMetadata databaseMetadata) {
        this.repository = repository;
        this.databaseMetadata = databaseMetadata;
    }

    public CodeModel load() {
        TypeMapper typeMapper = new TypeMapper();
        ObjectNameProvider objectNameProvider = new ObjectNameProvider();
        List<RecordModel> recordModels = new ArrayList<>();
        List<DaoModel> daoModels = new ArrayList<>();
        List<ImplementationDaoModel> implementationDaoModels = new ArrayList<>();

        for (TableMetadata table : databaseMetadata.tables()) {
            if (!table.name().equals("test_tag")) {
                continue;
            }

            if (isTableExcluded(table.name())) {
                continue;
            }

            // fieldModels --------------------------------------------------------------------------------------------------
            List<RecordFieldModel> fieldModels = new ArrayList<>();

            for (ColumnMetadata column : table.columns()) {
                fieldModels.add(new RecordFieldModel(
                    typeMapper.map(column.type()),
                    objectNameProvider.getFieldName(column.name()),
                    objectNameProvider.getByInName(column.name()),
                    table.name(),
                    column.name(),
                    column.primaryKey(),
                    column.nullable()
                ));
            }

            // recordModel --------------------------------------------------------------------------------------------------
            String recordPackageName = repository.recordPackageName();
            String recordSimpleName = objectNameProvider.getRecordName(table.name());
            String recordName = recordPackageName + "." + recordSimpleName;

            RecordModel recordModel = new RecordModel(
                recordName,
                recordSimpleName,
                recordPackageName,
                table,
                fieldModels
            );

            recordModels.add(recordModel);

            DaoModel daoModel = new DaoModel(
                repository.daoPackageName() + "." + objectNameProvider.getDaoSimpleName(recordModel),
                repository.daoPackageName(),
                objectNameProvider.getDaoSimpleName(recordModel),
                recordModel
            );

            daoModels.add(daoModel);

            implementationDaoModels.add(new ImplementationDaoModel(
                repository.implementationDaoPackageName() + "." + objectNameProvider.getImplementationDaoSimpleName(daoModel),
                repository.implementationDaoPackageName(),
                objectNameProvider.getImplementationDaoSimpleName(daoModel),
                daoModel,
                recordModel
            ));
        }

        return new CodeModel(
            unmodifiableList(recordModels),
            unmodifiableList(daoModels),
            unmodifiableList(implementationDaoModels)
        );
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
}
