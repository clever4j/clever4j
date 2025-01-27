package com.clever4j.rdbgenerator.recordgenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.RecordModel;
import com.clever4j.rdbgenerator.configuration.TemplateProcessor;
import freemarker.template.Template;
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

    public void generate(String distinctionDirectory) throws IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();

        // model.put("packageName", packageName);
        // model.put("className", className);
        // model.put("table", table.name());
        // model.put("fields", fields);

        Template template = templateProcessor.getTemplate("record.ftlh");

        templateProcessor.process(model, "record.ftlh", "%s/%s.java".formatted(distinctionDirectory, record.name()));
        // try (FileWriter fileWriter = new FileWriter()) {
        //     template.process(model, fileWriter);
        // }
    }
}
