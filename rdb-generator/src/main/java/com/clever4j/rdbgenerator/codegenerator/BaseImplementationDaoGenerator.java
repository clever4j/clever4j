package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.*;
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

@AllNonnullByDefault
public class BaseImplementationDaoGenerator {

    private final BaseImplementationDaoModel baseImplementationDaoModel;
    private final Path output;
    private final TemplateProcessor templateProcessor;

    public BaseImplementationDaoGenerator(BaseImplementationDaoModel baseImplementationDaoModel, Path output, TemplateProcessor templateProcessor) {
        this.baseImplementationDaoModel = baseImplementationDaoModel;
        this.output = output;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        Map<String, Object> model = new HashMap<>();

        model.put("name", baseImplementationDaoModel.name());
        model.put("packageName", baseImplementationDaoModel.packageName());
        model.put("simpleName", baseImplementationDaoModel.simpleName());
        model.put("database", baseImplementationDaoModel.database());
        model.put("recordModel", baseImplementationDaoModel.recordModel());
        model.put("baseDaoModel", baseImplementationDaoModel.templateDaoModel());
        model.put("daoModel", baseImplementationDaoModel.daoModel());

        // // functions
        model.put("generateCreateJavaType", new GenerateCreateJavaType());
        model.put("setStatementObject", new SetStatementObject());

        templateProcessor.processBaseImplementationDaoTemplate(model, "%s/%s.java".formatted(output, baseImplementationDaoModel.simpleName()));
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
