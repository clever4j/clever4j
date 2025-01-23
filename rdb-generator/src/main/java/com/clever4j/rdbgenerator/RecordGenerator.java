package com.clever4j.rdbgenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.Table;
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
    private final TemplateConfiguration templateConfiguration;
    private final DatabaseTypeMapper databaseTypeMapper;

    private String _package;
    private String _class;
    private List<RecordField> fields;

    public RecordGenerator(String _package, Table table, TemplateConfiguration templateConfiguration, DatabaseTypeMapper databaseTypeMapper) {
        this.table = table;
        this.templateConfiguration = templateConfiguration;
        this.databaseTypeMapper = databaseTypeMapper;

        // load fields
        this._package = _package;
        this._class = NAMING_STYLE_CONVERTER.convert(table.name(), NamingStyle.PASCAL_CASE);
        this.fields = table.columns().stream()
            .map(column -> {
                return new RecordField(
                    databaseTypeMapper.map(column.type()),
                    NAMING_STYLE_CONVERTER.convert(column.name(), NamingStyle.CAMEL_CASE),
                    column.name(),
                    column.primaryKey(),
                    column.nullable()
                );
            }).toList();
    }

    public void generate(String distinctionDirectory) throws IOException, TemplateException {
        String className = NAMING_STYLE_CONVERTER.convert(table.name(), NamingStyle.PASCAL_CASE);

        // Create the model hash. We use a Map here, but it could be a JavaBean too.
        Map<String, Object> model = new HashMap<>();

        model.put("_package", _package);
        model.put("_class", _class);
        model.put("table", table.name());
        model.put("fields", fields);

        Template template = templateConfiguration.getTemplate("record.ftlh");

        try (FileWriter fileWriter = new FileWriter("%s/%s.java".formatted(distinctionDirectory, className))) {
            template.process(model, fileWriter);
        }
    }

    public record RecordField(
        String type,
        String name,
        String colum,
        boolean primaryKey,
        boolean nullable
    ) {

    }
}
