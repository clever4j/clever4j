package com.clever4j.rdbgenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.Table;
import com.clever4j.text.NamingStyleConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@AllNonnullByDefault
public class RecordCodeGenerator {

    private final NamingStyleConverter namingStyleConverter = new NamingStyleConverter();

    public void generate(Table table, String distinctionDirectory) throws IOException, TemplateException {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);

        cfg.setClassForTemplateLoading(this.getClass(), "/templates/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());

        // Create the root hash. We use a Map here, but it could be a JavaBean too.
        Map<String, Object> root = new HashMap<>();

        // Put string "user" into the root
        root.put("package", "com.traisit.domain.database.test");
        root.put("className", "AuthSessionRecordTemplate");
        root.put("tableName", "auth_session");

        Template temp = cfg.getTemplate("record.ftlh");

        Writer out = new OutputStreamWriter(System.out);

        temp.process(root, out);
    }

    private String mapColumnTypeToFieldType(String columnType) {
        return switch (columnType) {
            case "uuid" -> "java.lang.String";
            case "timestamp" -> "java.time.LocalDateTime";
            case "varchar" -> "java.lang.String";
            case "text" -> "java.lang.String";
            case "bool" -> "java.lang.Boolean";
            case "int4" -> "java.lang.Integer";
            default -> "java.lang.String";
        };
    }
}
