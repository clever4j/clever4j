package com.clever4j.rdbgenerator.recordgenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.Column;
import com.clever4j.rdb.metadata.Table;
import com.clever4j.rdbgenerator.TypeMapper;
import com.clever4j.rdbgenerator.configuration.TemplateProcessor;
import com.clever4j.text.NamingStyleConverter;
import com.clever4j.text.NamingStyleConverter.NamingStyle;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllNonnullByDefault
public class RecordGenerator {

    private static final NamingStyleConverter NAMING_STYLE_CONVERTER = new NamingStyleConverter();

    private final Table table;
    private final TemplateProcessor templateProcessor;

    private String packageName;
    private String className;
    private List<RecordField> fields;

    public RecordGenerator(String packageName, Table table, TemplateProcessor templateProcessor, TypeMapper typeMapper) {
        this.table = table;
        this.templateProcessor = templateProcessor;

        // load fields
        this.packageName = packageName;
        this.className = NAMING_STYLE_CONVERTER.convert(table.name(), NamingStyle.PASCAL_CASE) + "Record";
        this.fields = table.columns().stream()
            .map(column -> {
                return new RecordField(
                    typeMapper.map(column.type().name()),
                    NAMING_STYLE_CONVERTER.convert(column.name(), NamingStyle.CAMEL_CASE),
                    column.name(),
                    column.primaryKey(),
                    column.nullable(),
                    column
                );
            }).toList();
    }

    public void generate(String distinctionDirectory) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        model.put("packageName", packageName);
        model.put("className", className);
        model.put("table", table.name());
        model.put("fields", fields);

        Template template = templateProcessor.getTemplate("record.ftlh");

        try (FileWriter fileWriter = new FileWriter("%s/%s.java".formatted(distinctionDirectory, className))) {
            template.process(model, fileWriter);
        }
    }

    public String getClassName() {
        return className;
    }

    public Table getTable() {
        return table;
    }

    public String getPackage() {
        return packageName;
    }

    public List<RecordField> getFields() {
        return fields;
    }

    public record RecordField(
        Class<?> type,
        String name,
        String colum,
        boolean primaryKey,
        boolean nullable,
        Column column
    ) {

    }
}
