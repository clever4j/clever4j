package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.ColumnMetadata;
import com.clever4j.rdb.metadata.DatabaseMetadata;
import com.clever4j.rdb.metadata.TableMetadata;
import com.clever4j.rdbgenerator.configuration.Database;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;

@AllNonnullByDefault
public final class CodeModelLoader {

    private final Database database;
    private final DatabaseMetadata databaseMetadata;

    public CodeModelLoader(Database database, DatabaseMetadata databaseMetadata) {
        this.database = database;
        this.databaseMetadata = databaseMetadata;
    }

    public CodeModel load() {
        TypeMapper typeMapper = new TypeMapper();
        ObjectNameProvider objectNameProvider = new ObjectNameProvider();

        List<RecordModel> recordModels = new ArrayList<>();
        List<BaseDaoModel> baseDaoModels = new ArrayList<>();
        List<DaoModel> daoModels = new ArrayList<>();
        List<BaseImplementationDaoModel> baseImplementationDaoModels = new ArrayList<>();
        List<ImplementationDaoModel> implementationDaoModels = new ArrayList<>();

        for (TableMetadata table : databaseMetadata.tables()) {
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
            String recordPackageName = database.recordPackageName();
            String recordSimpleName = objectNameProvider.getRecordName(table.name());
            String recordName = recordPackageName + "." + recordSimpleName;

            RecordModel recordModel = new RecordModel(
                recordName,
                recordSimpleName,
                recordPackageName,
                table,
                fieldModels,
                fieldModels.stream().filter(RecordFieldModel::primaryKey).toList(),
                database
            );

            recordModels.add(recordModel);

            // baseDaoModel --------------------------------------------------------------------------------------------
            BaseDaoModel baseDaoModel = new BaseDaoModel(
                database.baseDaoPackageName() + "." + objectNameProvider.getBaseDaoSimpleName(table.name()),
                database.baseDaoPackageName(),
                objectNameProvider.getBaseDaoSimpleName(table.name()),
                database,
                recordModel
            );

            baseDaoModels.add(baseDaoModel);

            // daoModel ------------------------------------------------------------------------------------------------
            DaoModel daoModel = new DaoModel(
                database.daoPackageName() + "." + objectNameProvider.getDaoSimpleName(table.name()),
                database.daoPackageName(),
                objectNameProvider.getDaoSimpleName(table.name()),
                recordModel,
                baseDaoModel,
                database
            );

            daoModels.add(daoModel);

            // baseImplementationDaoModel ------------------------------------------------------------------------------
            BaseImplementationDaoModel baseImplementationDaoModel = new BaseImplementationDaoModel(
                database.baseImplementationDaoPackageName() + "." + objectNameProvider.getBaseImplementationDaoSimpleName(table.name()),
                database.baseImplementationDaoPackageName(),
                objectNameProvider.getBaseImplementationDaoSimpleName(table.name()),
                database,
                recordModel,
                baseDaoModel,
                daoModel
            );

            baseImplementationDaoModels.add(baseImplementationDaoModel);

            // implementationDaoModel ----------------------------------------------------------------------------------
            ImplementationDaoModel implementationDaoModel = new ImplementationDaoModel(
                database.implementationDaoPackageName() + "." + objectNameProvider.getImplementationDaoSimpleName(table.name()),
                database.implementationDaoPackageName(),
                objectNameProvider.getImplementationDaoSimpleName(table.name()),
                database,
                recordModel,
                baseDaoModel,
                daoModel,
                baseImplementationDaoModel
            );

            implementationDaoModels.add(implementationDaoModel);
        }

        return new CodeModel(
            unmodifiableList(recordModels),
            unmodifiableList(baseDaoModels),
            unmodifiableList(daoModels),
            unmodifiableList(baseImplementationDaoModels),
            unmodifiableList(implementationDaoModels)
        );
    }

    private boolean isTableExcluded(String tableName) {
        return database.excludeTables().contains(tableName);
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
