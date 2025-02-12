package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.RecordModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class RecordGenerator {

    private final RecordModel record;
    private final Path output;
    private final TemplateProcessor templateProcessor;

    public RecordGenerator(RecordModel record, Path output, TemplateProcessor templateProcessor) {
        this.record = record;
        this.output = output;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        Map<String, Object> model = new HashMap<>();

        model.put("packageName", record.packageName());
        model.put("simpleName", record.simpleName());
        model.put("name", record.name());
        model.put("table", record.table());
        model.put("fields", record.fields());

        templateProcessor.processRecordTemplate(model, "%s/%s.java".formatted(output, record.simpleName()));
    }
}
