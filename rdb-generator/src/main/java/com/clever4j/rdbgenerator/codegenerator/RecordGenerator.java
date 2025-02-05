package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.RecordModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class RecordGenerator {

    private final RecordModel record;
    private final TemplateProcessor templateProcessor;

    public RecordGenerator(RecordModel record, TemplateProcessor templateProcessor) {
        this.record = record;
        this.templateProcessor = templateProcessor;
    }

    public void generate(String distinctionDirectory) {
        Map<String, Object> model = new HashMap<>();

        model.put("packageName", record.packageName());
        model.put("name", record.name());
        model.put("tableName", record.tableName());
        model.put("fields", record.fields());

        templateProcessor.processRecordTemplate(model, "%s/%s.java".formatted(distinctionDirectory, record.name()));
    }
}
