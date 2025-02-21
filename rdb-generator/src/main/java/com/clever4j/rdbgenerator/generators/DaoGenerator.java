package com.clever4j.rdbgenerator.generators;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.DaoModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class DaoGenerator {

    private final DaoModel daoModel;
    private final TemplateProcessor templateProcessor;

    public DaoGenerator(
        DaoModel daoModel,
        TemplateProcessor templateProcessor
    ) {
        this.daoModel = daoModel;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        if (Files.exists(daoModel.output())) {
            return;
        }

        Map<String, Object> model = new HashMap<>();

        model.put("name", daoModel.name());
        model.put("packageName", daoModel.packageName());
        model.put("simpleName", daoModel.simpleName());
        model.put("recordModel", daoModel.recordModel());
        model.put("templateDaoModel", daoModel.daoTemplateModel());
        model.put("database", daoModel.database());

        templateProcessor.processDaoTemplate(model, daoModel.output().toString());
    }
}
