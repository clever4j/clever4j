package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.ImplementationDaoTemplateModel;
import com.clever4j.rdbgenerator.codemodel.RecordFieldModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;
import freemarker.ext.beans.GenericObjectModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllNonnullByDefault
public class ImplementationDaoTemplateGenerator {

    private final ImplementationDaoTemplateModel implementationDaoTemplateModel;
    private final TemplateProcessor templateProcessor;

    public ImplementationDaoTemplateGenerator(ImplementationDaoTemplateModel implementationDaoTemplateModel, TemplateProcessor templateProcessor) {
        this.implementationDaoTemplateModel = implementationDaoTemplateModel;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        Map<String, Object> model = new HashMap<>();

        model.put("name", implementationDaoTemplateModel.name());
        model.put("packageName", implementationDaoTemplateModel.packageName());
        model.put("simpleName", implementationDaoTemplateModel.simpleName());
        model.put("database", implementationDaoTemplateModel.database());
        model.put("recordModel", implementationDaoTemplateModel.recordModel());
        model.put("baseDaoModel", implementationDaoTemplateModel.daoTemplateModel());
        model.put("daoModel", implementationDaoTemplateModel.daoModel());

        // // functions
        model.put("generateCreateJavaType", new GenerateCreateJavaType());
        model.put("setStatementObject", new SetStatementObject());

        templateProcessor.processImplementationDaoTemplate(model, implementationDaoTemplateModel.output().toString());
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
