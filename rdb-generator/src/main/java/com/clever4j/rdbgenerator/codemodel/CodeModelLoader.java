package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.ColumnMetadata;
import com.clever4j.rdb.metadata.DatabaseMetadata;
import com.clever4j.rdb.metadata.TableMetadata;
import com.clever4j.rdbgenerator.configuration.Database;

import java.nio.file.Path;
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
    private final ObjectNameProvider objectNameProvider = new ObjectNameProvider();

    public CodeModelLoader(Database database, DatabaseMetadata databaseMetadata) {
        this.database = database;
        this.databaseMetadata = databaseMetadata;
    }

    public CodeModel load() {
        TypeMapper typeMapper = new TypeMapper();

        List<RecordModel> recordModels = new ArrayList<>();
        List<TemplateDaoModel> templateDaoModels = new ArrayList<>();
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
            RecordModel recordModel = createRecordModel(table, fieldModels);

            recordModels.add(recordModel);

            // templateDaoModel --------------------------------------------------------------------------------------------
            TemplateDaoModel templateDaoModel = createTemplateDaoModel(table, recordModel);

            templateDaoModels.add(templateDaoModel);

            // daoModel ------------------------------------------------------------------------------------------------
            daoModels.add(createDaoModel(table, recordModel, templateDaoModel));

            // // baseImplementationDaoModel ------------------------------------------------------------------------------
            // BaseImplementationDaoModel baseImplementationDaoModel = new BaseImplementationDaoModel(
            //     database.baseImplementationDaoPackageName() + "." + objectNameProvider.getBaseImplementationDaoSimpleName(table.name()),
            //     database.baseImplementationDaoPackageName(),
            //     objectNameProvider.getBaseImplementationDaoSimpleName(table.name()),
            //     database,
            //     recordModel,
            //     templateDaoModel,
            //     daoModel
            // );

            // baseImplementationDaoModels.add(baseImplementationDaoModel);

            // // implementationDaoModel ----------------------------------------------------------------------------------
            // ImplementationDaoModel implementationDaoModel = new ImplementationDaoModel(
            //     database.implementationDaoPackageName() + "." + objectNameProvider.getImplementationDaoSimpleName(table.name()),
            //     database.implementationDaoPackageName(),
            //     objectNameProvider.getImplementationDaoSimpleName(table.name()),
            //     database,
            //     recordModel,
            //     templateDaoModel,
            //     daoModel,
            //     baseImplementationDaoModel
            // );

            // implementationDaoModels.add(implementationDaoModel);
        }

        return new CodeModel(
            unmodifiableList(recordModels),
            unmodifiableList(templateDaoModels),
            unmodifiableList(daoModels),
            unmodifiableList(baseImplementationDaoModels),
            unmodifiableList(implementationDaoModels)
        );
    }

    private RecordModel createRecordModel(TableMetadata table, List<RecordFieldModel> fieldModels) {
        String packageName = database.packageName() + ".records";
        String simpleName = objectNameProvider.getRecordName(table.name());
        String name = packageName + "." + simpleName;
        Path output = database.output().resolve(Path.of("records", simpleName + ".java"));

        return new RecordModel(
            name,
            simpleName,
            packageName,
            output,
            table,
            fieldModels,
            fieldModels.stream()
                .filter(RecordFieldModel::primaryKey)
                .toList(),
            database
        );

    }

    private TemplateDaoModel createTemplateDaoModel(TableMetadata table, RecordModel recordModel) {
        String simpleName = objectNameProvider.getTemplateDaoSimpleName(table.name());
        String packageName = database.packageName() + ".generated";
        String name = packageName + "." + simpleName;
        Path output = database.output().resolve(Path.of("generated", simpleName + ".java"));

        return  new TemplateDaoModel(name, packageName, output, simpleName, database, recordModel);
    }

    private DaoModel createDaoModel(TableMetadata table, RecordModel recordModel, TemplateDaoModel templateDaoModel) {
        String simpleName = objectNameProvider.getDaoSimpleName(table.name());
        String name = database.packageName() + "." + objectNameProvider.getDaoSimpleName(table.name());
        String packageName = database.packageName() + ".dao";
        Path output = database.output().resolve(Path.of("dao", simpleName + ".java"));

        return new DaoModel(
            name,
            packageName,
            output,
            simpleName,
            recordModel,
            templateDaoModel,
            database
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
