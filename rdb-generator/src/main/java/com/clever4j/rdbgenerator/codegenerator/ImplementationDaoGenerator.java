package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.fs.FsUtil;
import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.ImplementationDaoModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class ImplementationDaoGenerator {

    private final ImplementationDaoModel implementationDaoModel;
    private final TemplateProcessor templateProcessor;

    public ImplementationDaoGenerator(ImplementationDaoModel implementationDaoModel, TemplateProcessor templateProcessor) {
        this.implementationDaoModel = implementationDaoModel;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        String target = implementationDaoModel.output().toString();

        if (FsUtil.exists(target)) {
            return;
        }

        Map<String, Object> model = new HashMap<>();

        model.put("name", implementationDaoModel.name());
        model.put("packageName", implementationDaoModel.packageName());
        model.put("simpleName", implementationDaoModel.simpleName());
        model.put("database", implementationDaoModel.database());
        model.put("recordModel", implementationDaoModel.recordModel());
        model.put("daoModel", implementationDaoModel.daoModel());
        model.put("implementationDaoTemplateModel", implementationDaoModel.implementationDaoTemplateModel());

        templateProcessor.processImplementationDao(model, target);
    }
}
