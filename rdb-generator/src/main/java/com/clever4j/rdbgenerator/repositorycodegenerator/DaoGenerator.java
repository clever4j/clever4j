package com.clever4j.rdbgenerator.repositorycodegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.DaoModel;
import com.clever4j.rdbgenerator.codemodel.RecordField;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;
import freemarker.ext.beans.GenericObjectModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllNonnullByDefault
public class DaoGenerator {

    private final DaoModel daoModel;
    private final TemplateProcessor templateProcessor;

    public DaoGenerator(
        DaoModel daoModel,
        TemplateProcessor templateProcessor
    ) {
        this.daoModel = daoModel;
        this.templateProcessor = templateProcessor;
    }

    public void generate(String distinctionDirectory) {
        Map<String, Object> model = new HashMap<>();

        model.put("packageName", daoModel.packageName());
        model.put("name", daoModel.name());
        model.put("tableName", daoModel.record().table().name());
        model.put("recordName", daoModel.record().name());
        model.put("recordFields", daoModel.record().fields());

        model.put("recordFieldsSize", daoModel.record().fields().size());

        List<RecordField> primaryKeyFields = daoModel.record().fields().stream()
            .filter(RecordField::primaryKey)
            .toList();

        model.put("primaryKeyFields", primaryKeyFields);

        String primaryKeyFieldParametersInline = daoModel.record().fields().stream()
            .filter(RecordField::primaryKey)
            .map(recordField -> {
                return "%s %s".formatted(recordField.type().getCanonicalName(), recordField.name());
            }).collect(Collectors.joining(", "));

        model.put("primaryKeyFieldParametersInline", primaryKeyFieldParametersInline);

        // columnsInline
        String columnsInline = daoModel.record().fields().stream()
            .map(recordField -> {
                return "\\\"%s\\\"".formatted(recordField.columnName());
            }).collect(Collectors.joining(", "));

        model.put("columnsInline", columnsInline);

        // columnsQuestionMarkJoined
        String columnsQuestionMarkJoined = daoModel.record().fields().stream()
            .map(recordField -> {
                return "?";
            }).collect(Collectors.joining(", "));

        model.put("columnsQuestionMarkJoined", columnsQuestionMarkJoined);

        // functions
        model.put("generateCreateJavaType", new GenerateCreateJavaType());
        model.put("setStatementObject", new SetStatementObject());

        templateProcessor.processDaoTemplate(model, "%s/%s.java".formatted(distinctionDirectory, daoModel.name()));
    }

    private static class GenerateCreateJavaType implements TemplateMethodModelEx {

        @Override
        public Object exec(List arguments) throws TemplateModelException {
            RecordField field = (RecordField) ((GenericObjectModel) arguments.getFirst()).getWrappedObject();

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
            System.out.println("test");
            return "?";
        }
    }
}
