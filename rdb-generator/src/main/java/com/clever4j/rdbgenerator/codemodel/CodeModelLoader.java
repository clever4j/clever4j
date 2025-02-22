package com.clever4j.rdbgenerator.codemodel;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.ColumnMetadata;
import com.clever4j.rdb.metadata.DatabaseMetadata;
import com.clever4j.rdb.metadata.TableMetadata;
import com.clever4j.rdbgenerator.configuration.Database;
import com.clever4j.text.NamingStyleConverter;
import com.clever4j.text.NamingStyleConverter.NamingStyle;

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
        List<DaoTemplateModel> daoTemplateModels = new ArrayList<>();
        List<DaoModel> daoModels = new ArrayList<>();
        List<ImplementationDaoTemplateModel> implementationDaoTemplateModels = new ArrayList<>();
        List<ImplementationDaoModel> implementationDaoModels = new ArrayList<>();

        for (TableMetadata table : databaseMetadata.tables()) {
            if (isTableExcluded(table.name())) {
                continue;
            }

            // fieldModels --------------------------------------------------------------------------------------------------
            List<RecordFieldModel> fieldModels = new ArrayList<>();

            for (ColumnMetadata column : table.columns()) {
                Class<?> type = typeMapper.map(column.type());

                fieldModels.add(new RecordFieldModel(
                    type,
                    type.getCanonicalName(),
                    objectNameProvider.getFieldName(column.name()),
                    objectNameProvider.formatPascalCase(column.name()),
                    objectNameProvider.getByInName(column.name()),
                    table.name(),
                    column.name(),
                    column.primaryKey(),
                    column.nullable()
                ));
            }

            // recordModel ---------------------------------------------------------------------------------------------
            RecordModel recordModel = createRecordModel(table, fieldModels);
            recordModels.add(recordModel);

            // daoTemplateModel ----------------------------------------------------------------------------------------
            DaoTemplateModel daoTemplateModel = createTemplateDaoModel(table, recordModel);
            daoTemplateModels.add(daoTemplateModel);

            // daoModel ------------------------------------------------------------------------------------------------
            DaoModel daoModel = createDaoModel(table, recordModel, daoTemplateModel);
            daoModels.add(daoModel);

            // implementationDaoTemplateModel --------------------------------------------------------------------------
            ImplementationDaoTemplateModel implementationDaoTemplateModel = createImplementationDaoTemplateModel(table,
                recordModel, daoTemplateModel, daoModel);
            implementationDaoTemplateModels.add(implementationDaoTemplateModel);

            // implementationDaoModel ----------------------------------------------------------------------------------
            implementationDaoModels.add(createImplementationDaoModel(table, recordModel, daoTemplateModel, daoModel,
                implementationDaoTemplateModel));
        }

        return new CodeModel(
            unmodifiableList(recordModels),
            unmodifiableList(daoTemplateModels),
            unmodifiableList(daoModels),
            unmodifiableList(implementationDaoTemplateModels),
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

    private DaoTemplateModel createTemplateDaoModel(TableMetadata table, RecordModel recordModel) {
        String simpleName = objectNameProvider.getTemplateDaoSimpleName(table.name());
        String packageName = database.packageName() + ".generated";
        String name = packageName + "." + simpleName;
        Path output = database.output().resolve(Path.of("generated", simpleName + ".java"));

        return new DaoTemplateModel(name, packageName, output, simpleName, database, recordModel);
    }

    private DaoModel createDaoModel(TableMetadata table, RecordModel recordModel, DaoTemplateModel daoTemplateModel) {
        String simpleName = objectNameProvider.getDaoSimpleName(table.name());
        String packageName = database.packageName() + ".dao";
        String name = packageName + "." + simpleName;
        Path output = database.output().resolve(Path.of("dao", simpleName + ".java"));

        return new DaoModel(name, packageName, output, simpleName, recordModel, daoTemplateModel, database);
    }

    private ImplementationDaoTemplateModel createImplementationDaoTemplateModel(TableMetadata table,
        RecordModel recordModel, DaoTemplateModel daoTemplateModel, DaoModel daoModel) {

        String packageName = database.packageName() + ".generated";
        String simpleName = objectNameProvider.getImplementationDaoTemplateSimpleName(table.name());
        String name = packageName + "." + simpleName;
        Path output = database.output().resolve(Path.of("generated", simpleName + ".java"));

        return new ImplementationDaoTemplateModel(name, packageName, output, simpleName, database, recordModel,
            daoTemplateModel, daoModel);
    }

    private ImplementationDaoModel createImplementationDaoModel(TableMetadata table, RecordModel recordModel, DaoTemplateModel daoTemplateModel,
        DaoModel daoModel, ImplementationDaoTemplateModel implementationDaoTemplateModel) {

        String packageName = database.packageName() + ".dao";
        String simpleName = objectNameProvider.getImplementationDaoSimpleName(table.name());
        String name = packageName + "." + simpleName;
        Path output = database.output().resolve(Path.of("dao", simpleName + ".java"));

        return new ImplementationDaoModel(name, packageName, output, simpleName, database, recordModel,
            daoTemplateModel, daoModel, implementationDaoTemplateModel);

    }

    private boolean isTableExcluded(String tableName) {
        if (database.onlyTables() != null) {
            return !database.onlyTables().contains(tableName);
        }

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
