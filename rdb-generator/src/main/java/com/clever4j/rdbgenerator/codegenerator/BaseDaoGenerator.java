package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.BaseDaoModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class BaseDaoGenerator {

    private final BaseDaoModel baseDaoModel;
    private final Path output;
    private final TemplateProcessor templateProcessor;

    public BaseDaoGenerator(BaseDaoModel baseDaoModel, Path output, TemplateProcessor templateProcessor) {
        this.baseDaoModel = baseDaoModel;
        this.output = output;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        Map<String, Object> model = new HashMap<>();

        model.put("name", baseDaoModel.name());
        model.put("packageName", baseDaoModel.packageName());
        model.put("simpleName", baseDaoModel.simpleName());
        model.put("database", baseDaoModel.database());
        model.put("recordModel", baseDaoModel.recordModel());

        templateProcessor.processBaseDaoTemplate(model, "%s/%s.java".formatted(output, baseDaoModel.simpleName()));
    }
}
