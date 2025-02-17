package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.fs.FsUtil;
import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.ImplementationDaoModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class ImplementationDaoGenerator {

    private final ImplementationDaoModel implementationDaoModel;
    private final Path output;
    private final TemplateProcessor templateProcessor;

    public ImplementationDaoGenerator(ImplementationDaoModel implementationDaoModel, Path output, TemplateProcessor templateProcessor) {
        this.implementationDaoModel = implementationDaoModel;
        this.output = output;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        String target = "%s/%s.java".formatted(output, implementationDaoModel.simpleName());

        if (FsUtil.exists(target)) {
            return;
        }

        Map<String, Object> model = new HashMap<>();

        model.put("name", implementationDaoModel.name());
        model.put("packageName", implementationDaoModel.packageName());
        model.put("simpleName", implementationDaoModel.simpleName());
        model.put("database", implementationDaoModel.database());
        model.put("recordModel", implementationDaoModel.record());
        model.put("daoModel", implementationDaoModel.daoModel());
        model.put("baseImplementationDaoModel", implementationDaoModel.baseImplementationDaoModel());

        templateProcessor.processImplementationDaoTemplate(model, "%s/%s.java".formatted(output, implementationDaoModel.simpleName()));
    }
}
