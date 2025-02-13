package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.DaoModel;
import com.clever4j.rdbgenerator.codemodel.ImplementationDaoModel;
import com.clever4j.rdbgenerator.codemodel.RecordFieldModel;
import com.clever4j.rdbgenerator.codemodel.RecordModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;
import freemarker.ext.beans.GenericObjectModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllNonnullByDefault
public class ImplementationDaoGenerator {

    private final ImplementationDaoModel implementationDaoModel;
    private final Path output;
    private final TemplateProcessor templateProcessor;

    public ImplementationDaoGenerator(ImplementationDaoModel implementationDaoModel, Path output, TemplateProcessor templateProcessor) {
        this.implementationDaoModel = implementationDaoModel;
        this.output = output;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        Map<String, Object> model = new HashMap<>();

        model.put("name", implementationDaoModel.name());
        model.put("packageName", implementationDaoModel.packageName());
        model.put("simpleName", implementationDaoModel.simpleName());
        model.put("daoModel", implementationDaoModel.daoModel());
        model.put("record", implementationDaoModel.record());

        // model.put("packageName", implementationDaoModel.packageName());
        // model.put("name", implementationDaoModel.name());
        // model.put("tableName", implementationDaoModel.record().table().name());
        // model.put("recordName", implementationDaoModel.record().name());
        // model.put("recordFields", implementationDaoModel.record().fields());

        // model.put("recordFieldsSize", implementationDaoModel.record().fields().size());

        // List<RecordFieldModel> primaryKeyFields = implementationDaoModel.record().fields().stream()
        //     .filter(RecordFieldModel::primaryKey)
        //     .toList();

        // model.put("primaryKeyFields", primaryKeyFields);

        // String primaryKeyFieldParametersInline = implementationDaoModel.record().fields().stream()
        //     .filter(RecordFieldModel::primaryKey)
        //     .map(recordField -> {
        //         return "%s %s".formatted(recordField.type().getCanonicalName(), recordField.name());
        //     }).collect(Collectors.joining(", "));

        // model.put("primaryKeyFieldParametersInline", primaryKeyFieldParametersInline);

        // // columnsInline
        // String columnsInline = implementationDaoModel.record().fields().stream()
        //     .map(recordField -> {
        //         return "\\\"%s\\\"".formatted(recordField.columnName());
        //     }).collect(Collectors.joining(", "));

        // model.put("columnsInline", columnsInline);

        // // columnsQuestionMarkJoined
        // String columnsQuestionMarkJoined = implementationDaoModel.record().fields().stream()
        //     .map(recordField -> {
        //         return "?";
        //     }).collect(Collectors.joining(", "));

        // model.put("columnsQuestionMarkJoined", columnsQuestionMarkJoined);

        // // functions
        // model.put("generateCreateJavaType", new GenerateCreateJavaType());
        // model.put("setStatementObject", new SetStatementObject());

        templateProcessor.processImplementationDaoTemplate(model, "%s/%s.java".formatted(output, implementationDaoModel.simpleName()));
    }

    private static class GenerateCreateJavaType implements TemplateMethodModelEx {

        @Override
        public Object exec(List arguments) throws TemplateModelException {
            RecordFieldModel field = (RecordFieldModel) ((GenericObjectModel) arguments.getFirst()).getWrappedObject();

            if (field.type().equals(int.class) || field.type().equals(Integer.class)) {
                return "resultSet.getInt(\"%s\")".formatted(field.columnName());
            } else if (field.type().equals(long.class) || field.type().equals(Long.class)) {
                return "resultSet.getLong(\"%s\")".formatted(field.columnName());
            } else if (field.type().equals(String.class)) {
                return "resultSet.getString(\"%s\")".formatted(field.columnName());
            } else if (field.type().equals(double.class) || field.type().equals(Double.class)) {
                return "resultSet.getDouble(\"%s\")".formatted(field.columnName());
            } else if (field.type().equals(boolean.class) || field.type().equals(Boolean.class)) {
                return "resultSet.getBoolean(\"%s\")".formatted(field.columnName());
            } else if (field.type().equals(UUID.class)) {
                return "(%s) resultSet.getObject(\"%s\")".formatted(UUID.class.getCanonicalName(), field.columnName());
            } else if (field.type().equals(LocalDateTime.class)) {
                return "resultSet.getTimestamp(\"%s\") == null ? null : resultSet.getTimestamp(\"%s\").toLocalDateTime()".formatted(field.columnName(), field.columnName());
            } else {
                throw new RuntimeException("Unsupported type");
            }
        }
    }

    private static class SetStatementObject implements TemplateMethodModelEx {

        @Override
        public Object exec(List arguments) throws TemplateModelException {
            return "?";
        }
    }
}
