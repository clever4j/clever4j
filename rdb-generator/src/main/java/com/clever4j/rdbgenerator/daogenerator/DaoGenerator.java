package com.clever4j.rdbgenerator.daogenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.Table;
import com.clever4j.rdbgenerator.TypeMapper;
import com.clever4j.rdbgenerator.configuration.TemplateProcessor;
import com.clever4j.rdbgenerator.generators.CodePartGenerator;
import com.clever4j.rdbgenerator.recordgenerator.RecordGenerator;
import com.clever4j.rdbgenerator.recordgenerator.RecordGenerator.RecordField;
import com.clever4j.text.NamingStyleConverter;
import com.clever4j.text.NamingStyleConverter.NamingStyle;
import freemarker.ext.beans.GenericObjectModel;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllNonnullByDefault
public class DaoGenerator {

    private static final NamingStyleConverter NAMING_STYLE_CONVERTER = new NamingStyleConverter();

    private final Table table;
    private final TemplateProcessor templateProcessor;
    private final TypeMapper typeMapper;
    private final CodePartGenerator codePartGenerator = new CodePartGenerator();

    private String packageName;
    private String className;
    private RecordGenerator recordGenerator;

    public DaoGenerator(String packageName, Table table, RecordGenerator recordGenerator, TemplateProcessor templateProcessor, TypeMapper typeMapper) {
        this.table = table;
        this.recordGenerator = recordGenerator;
        this.templateProcessor = templateProcessor;
        this.typeMapper = typeMapper;

        // load fields
        this.packageName = packageName;
        this.className = NAMING_STYLE_CONVERTER.convert(table.name(), NamingStyle.PASCAL_CASE) + "Dao";
    }

    public void generate(String distinctionDirectory) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        model.put("packageName", packageName);
        model.put("className", className);
        model.put("tableName", table.name());
        model.put("recordClassName", recordGenerator.getClassName());
        model.put("recordFields", recordGenerator.getFields());

        List<RecordField> primaryKeyFields = recordGenerator.getFields().stream()
            .filter(RecordField::primaryKey)
            .toList();

        model.put("primaryKeyFields", primaryKeyFields);

        String primaryKeyFieldParametersInline = recordGenerator.getFields().stream()
            .filter(RecordField::primaryKey)
            .map(recordField -> {
                return "%s %s".formatted(recordField.type().getCanonicalName(), recordField.name());
            }).collect(Collectors.joining(", "));

        model.put("primaryKeyFieldParametersInline", primaryKeyFieldParametersInline);

        // columnsInline
        String columnsInline = recordGenerator.getFields().stream()
            .map(recordField -> {
                return "\\\"%s\\\"".formatted(recordField.column().name());
            }).collect(Collectors.joining(", "));

        model.put("columnsInline", columnsInline);

        // columnsQuestionMarkJoined
        String columnsQuestionMarkJoined = recordGenerator.getFields().stream()
            .map(recordField -> {
                return "?";
            }).collect(Collectors.joining(", "));

        model.put("columnsQuestionMarkJoined", columnsQuestionMarkJoined);

        // functions
        model.put("generateCreateJavaType", new GenerateCreateJavaType());

        Template template = templateProcessor.getTemplate("dao.ftlh");

        try (FileWriter fileWriter = new FileWriter("%s/%s.java".formatted(distinctionDirectory, className))) {
            template.process(model, fileWriter);
        }
    }

    private class GenerateCreateJavaType implements TemplateMethodModelEx {

        @Override
        public Object exec(List arguments) throws TemplateModelException {
            RecordField field = (RecordField) ((GenericObjectModel) arguments.getFirst()).getWrappedObject();

            if (field.type().equals(int.class) || field.type().equals(Integer.class)) {
                return "resultSet.getInt(\"%s\")".formatted(field.column().name());
            } else if (field.type().equals(long.class) || field.type().equals(Long.class)) {
                return "resultSet.getLong(\"%s\")".formatted(field.column().name());
            } else if (field.type().equals(String.class)) {
                return "resultSet.getString(\"%s\")".formatted(field.column().name());
            } else if (field.type().equals(double.class) || field.type().equals(Double.class)) {
                return "resultSet.getDouble(\"%s\")".formatted(field.column().name());
            } else if (field.type().equals(boolean.class) || field.type().equals(Boolean.class)) {
                return "resultSet.getBoolean(\"%s\")".formatted(field.column().name());
            } else if (field.type().equals(UUID.class)) {
                return "(%s) resultSet.getObject(\"%s\")".formatted(UUID.class.getCanonicalName(), field.column().name());
            } else if (field.type().equals(LocalDateTime.class)) {
                return "resultSet.getTimestamp(\"%s\") == null ? null : resultSet.getTimestamp(\"%s\").toLocalDateTime()".formatted(field.column().name(), field.column().name());
            } else {
                throw new RuntimeException("Unsupported type");
            }
        }
    }
}
