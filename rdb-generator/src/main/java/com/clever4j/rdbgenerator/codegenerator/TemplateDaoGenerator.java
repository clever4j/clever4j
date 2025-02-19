package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.TemplateDaoModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class TemplateDaoGenerator {

    private final TemplateDaoModel templateDaoModel;
    private final TemplateProcessor templateProcessor;

    public TemplateDaoGenerator(TemplateDaoModel templateDaoModel, TemplateProcessor templateProcessor) {
        this.templateDaoModel = templateDaoModel;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        Map<String, Object> model = new HashMap<>();

        model.put("name", templateDaoModel.name());
        model.put("packageName", templateDaoModel.packageName());
        model.put("simpleName", templateDaoModel.simpleName());
        model.put("database", templateDaoModel.database());
        model.put("recordModel", templateDaoModel.recordModel());

        templateProcessor.processBaseDaoTemplate(model, templateDaoModel.output().toString());
    }
}
