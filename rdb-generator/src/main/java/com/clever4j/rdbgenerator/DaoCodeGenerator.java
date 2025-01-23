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
//        Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
//        // cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));
//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//        cfg.setLogTemplateExceptions(false);
//        cfg.setWrapUncheckedExceptions(true);
//        cfg.setFallbackOnNullLoopVariable(false);
//        cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
//
//        try {
//            // Ustawienie katalogu z szablonami
//            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
//            cfg.setDefaultEncoding("UTF-8");
//
//            // Załadowanie szablonu
//            Template template = cfg.getTemplate("template.ftl");
//
//            // Przygotowanie danych do wstrzyknięcia w szablon
//            Map<String, Object> data = new HashMap<>();
//            data.put("name", "Jan Kowalski");
//            data.put("tasks", Arrays.asList("Zrobić zakupy", "Napisać raport", "Zadzwonić do klienta"));
//
//            // Generowanie pliku HTML
//            try (Writer fileWriter = new FileWriter("output.html")) {
//                template.process(data, fileWriter);
//            }
//
//            System.out.println("Plik HTML został wygenerowany: output.html");
//        } catch (IOException | TemplateException e) {
//            e.printStackTrace();
//        }
//
//
    }

    public void generate(Table table, String distinctionDirectory) {

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
