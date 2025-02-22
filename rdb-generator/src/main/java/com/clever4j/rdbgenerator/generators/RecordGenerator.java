package com.clever4j.rdbgenerator.generators;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.metadata.TableMetadata;
import com.clever4j.rdbgenerator.codemodel.RecordFieldModel;
import com.clever4j.rdbgenerator.codemodel.RecordModel;
import com.clever4j.rdbgenerator.configuration.Database;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllNonnullByDefault
public class RecordGenerator {

    private final RecordModel record;
    private final TemplateProcessor templateProcessor;

    public RecordGenerator(RecordModel record, TemplateProcessor templateProcessor) {
        this.record = record;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        Map<String, Object> model = new HashMap<>();

        model.put("record", record);

        // model.put("name", record.name());
        // model.put("simpleName", record.simpleName());
        // model.put("packageName", record.packageName());
        // model.put("output", record.output());
        // model.put("table", record.table());
        // model.put("fields", record.fields());
        // model.put("primaryKeys", record.primaryKeys());
        model.put("database", record.database());

        templateProcessor.processRecordTemplate(model, record.output().toString());
    }
}
