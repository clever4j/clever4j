package com.clever4j.rdbgenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.Column;
import com.clever4j.rdb.metadata.Table;
import com.clever4j.text.NamingStyleConverter;
import com.clever4j.text.NamingStyleConverter.NamingStyle;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.TimeZone;

@AllNonnullByDefault
public class DaoCodeGenerator {

    private final NamingStyleConverter namingStyleConverter = new NamingStyleConverter();

    public void generateV1() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
        // cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());

    }

    public void generate(Table table, String distinctionDirectory) {
        StringBuilder code = new StringBuilder();

        String className = namingStyleConverter.convert(table.name(), NamingStyle.PASCAL_CASE) + "Dao";
        String recordName = namingStyleConverter.convert(table.name(), NamingStyle.PASCAL_CASE) + "Record";

        code.append("package com.traisit.domain.database.test;\n");
        code.append("\n");
        code.append("import java.sql.Connection;\n");
        code.append("import java.sql.PreparedStatement;\n");
        code.append("import java.sql.ResultSet;\n");
        code.append("import java.sql.SQLException;\n");
        code.append("import java.util.ArrayList;\n");
        code.append("import java.util.List;\n");
        code.append("\n");
        code.append("public class %s {\n".formatted(className));

        // getAll ------------------------------------------------------------------------------------------------------
        code.append("    public List<AuthSessionRecord> getAll(Connection connection) {\n");
        code.append("        StringBuilder sql = new StringBuilder();\n");
        code.append("        List<AuthSessionRecord> result = new ArrayList<>();\n");
        code.append("\n");

        StringBuilder columnsCodes = new StringBuilder();

        for (int i = 0; i < table.columns().size(); i++) {
            Column column = table.columns().get(i);

            columnsCodes.append(" ").append(column.name());

            if (i < table.columns().size() - 1) {
                columnsCodes.append(",");
            }
        }

        code.append("        sql.append(\"SELECT ").append(columnsCodes).append(" FROM ").append(table.name()).append("\");\n");
        code.append("        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {\n");
        code.append("            ResultSet resultSet = statement.executeQuery();\n\n");
        code.append("            while (resultSet.next()) {\n");
        code.append("                result.add(new %s(\n".formatted(recordName));

        for (int i = 0; i < table.columns().size(); i++) {
            Column column = table.columns().get(i);

            code.append("                    resultSet.getInt(\"%s\")".formatted(column.name()));

            if (i < table.columns().size() - 1) {
                code.append(",\n");
            } else {
                code.append("\n);");
            }
        }

        code.append("                )\n");
        code.append("                );\n");
        code.append("            }\n");
        code.append("        } catch (SQLException e) {\n");
        code.append("            throw new RuntimeException(e);\n");
        code.append("        }\n\n");
        code.append("        return result;\n");
        code.append("    }\n");
        code.append("}\n");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(distinctionDirectory + "/" + className + ".java");
            fileOutputStream.write(code.toString().getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
