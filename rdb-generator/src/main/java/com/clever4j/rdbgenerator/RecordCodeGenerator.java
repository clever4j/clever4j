package com.clever4j.rdbgenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.Column;
import com.clever4j.rdb.metadata.Table;
import com.clever4j.text.NamingStyleConverter;
import com.clever4j.text.NamingStyleConverter.NamingStyle;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.PackageFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@AllNonnullByDefault
public class RecordCodeGenerator {

    private final NamingStyleConverter namingStyleConverter = new NamingStyleConverter();

    public void generate(Table table, String distinctionDirectory) {
        StringBuilder sb = new StringBuilder();

        String recordName = namingStyleConverter.convert(table.name(), NamingStyle.PASCAL_CASE) + "Record";

        sb.append("package com.traisit.domain.database.test;\n");
        sb.append("\n");
        sb.append("@jakarta.persistence.Table(name = \"%s\")\n".formatted(table.name()));
        // sb.append("@com.clever4j.lang.AllNonnullByDefault\n");
        sb.append("public record %s(\n".formatted(recordName));

        for (int i = 0; i < table.columns().size(); i++) {
            Column column = table.columns().get(i);
            String fieldType = mapColumnTypeToFieldType(column.type());
            String filedName = namingStyleConverter.convert(column.name(), NamingStyle.CAMEL_CASE);

            sb.append("\n");

            if (column.primaryKey()) {
                sb.append("    @jakarta.persistence.Id\n");
            }

            if (column.nullable()) {
                sb.append("    @jakarta.annotation.Nullable\n");
            } else {
                sb.append("    @jakarta.annotation.Nonnull\n");
            }

            sb.append("    %s %s".formatted(fieldType, filedName));

            if (i < table.columns().size() - 1) {
                sb.append(",\n");
            } else {
                sb.append("\n");
            }
        }

        sb.append(") {}");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(distinctionDirectory + "/" + recordName + ".java");
            fileOutputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
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
