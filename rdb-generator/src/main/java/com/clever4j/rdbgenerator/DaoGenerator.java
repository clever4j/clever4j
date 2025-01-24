package com.clever4j.rdbgenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.Table;
import com.clever4j.rdbgenerator.RecordGenerator.RecordField;
import com.clever4j.text.NamingStyleConverter;
import com.clever4j.text.NamingStyleConverter.NamingStyle;
import freemarker.ext.beans.GenericObjectModel;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllNonnullByDefault
public class DaoGenerator {

    private static final NamingStyleConverter NAMING_STYLE_CONVERTER = new NamingStyleConverter();

    private final Table table;
    private final TemplateConfiguration templateConfiguration;
    private final DatabaseTypeMapper databaseTypeMapper;
    private final CodePartGenerator codePartGenerator = new CodePartGenerator();

    private String packageName;
    private String className;
    private RecordGenerator recordGenerator;
    private List<String> partA;

    public DaoGenerator(String packageName, Table table, RecordGenerator recordGenerator, TemplateConfiguration templateConfiguration, DatabaseTypeMapper databaseTypeMapper) {
        this.table = table;
        this.recordGenerator = recordGenerator;
        this.templateConfiguration = templateConfiguration;
        this.databaseTypeMapper = databaseTypeMapper;

        // load fields
        this.packageName = packageName;
        this.className = NAMING_STYLE_CONVERTER.convert(table.name(), NamingStyle.PASCAL_CASE) + "Dao";
    }

    public void generate(String distinctionDirectory) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        model.put("packageName", packageName);
        model.put("className", className);
        model.put("recordGenerator", recordGenerator);
        model.put("table", table.name());

        // functions
        model.put("generateCreateJavaType", new GenerateCreateJavaType());

        // model.put("fields", fields);

        Template template = templateConfiguration.getTemplate("dao.ftlh");

        try (FileWriter fileWriter = new FileWriter("%s/%s.java".formatted(distinctionDirectory, className))) {
            template.process(model, fileWriter);
        }
    }

    private class GenerateCreateJavaType implements TemplateMethodModelEx {

        @Override
        public Object exec(List arguments) throws TemplateModelException {
            RecordField recordField = (RecordField) ((GenericObjectModel) arguments.get(0)).getWrappedObject();

            // codePartGenerator.generateCreateJavaType(recordField.type())

            // codePartGenerator.generateCreateJavaType()
            // String.valueOf(resultSet.getInt("auth_session_id"))

            // if (arguments.size() != 2) {
            //     throw new TemplateModelException("Wymagane są dwa argumenty");
            // }

            // double num1 = Double.parseDouble(arguments.get(0).toString());
            // double num2 = Double.parseDouble(arguments.get(1).toString());

            return "TEST";
        }
    }
}
