package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.DaoTemplateModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class DaoTemplateGenerator {

    private final DaoTemplateModel daoTemplateModel;
    private final TemplateProcessor templateProcessor;

    public DaoTemplateGenerator(DaoTemplateModel daoTemplateModel, TemplateProcessor templateProcessor) {
        this.daoTemplateModel = daoTemplateModel;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        Map<String, Object> model = new HashMap<>();

        model.put("name", daoTemplateModel.name());
        model.put("packageName", daoTemplateModel.packageName());
        model.put("simpleName", daoTemplateModel.simpleName());
        model.put("database", daoTemplateModel.database());
        model.put("recordModel", daoTemplateModel.recordModel());

        templateProcessor.processBaseDaoTemplate(model, daoTemplateModel.output().toString());
    }
}
