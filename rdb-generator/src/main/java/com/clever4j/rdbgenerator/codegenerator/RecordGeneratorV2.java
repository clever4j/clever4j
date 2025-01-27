package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.RecordModel;
import com.clever4j.rdbgenerator.configuration.TemplateProcessor;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class RecordGeneratorV2 {

    private final RecordModel record;
    private final TemplateProcessor templateProcessor;

    public RecordGeneratorV2(RecordModel record, TemplateProcessor templateProcessor) {
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
