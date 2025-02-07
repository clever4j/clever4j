package com.clever4j.rdbgenerator.repositorycodegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.Record;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class RecordGenerator {

    private final Record record;
    private final TemplateProcessor templateProcessor;

    public RecordGenerator(Record record, TemplateProcessor templateProcessor) {
        this.record = record;
        this.templateProcessor = templateProcessor;
    }

    public void generate(String distinctionDirectory) {
        Map<String, Object> model = new HashMap<>();

        model.put("packageName", record.packageName());
        model.put("name", record.name());
        model.put("tableName", record.table().name());
        model.put("fields", record.fields());

        templateProcessor.processRecordTemplate(model, "%s/%s.java".formatted(distinctionDirectory, record.name()));
    }
}
