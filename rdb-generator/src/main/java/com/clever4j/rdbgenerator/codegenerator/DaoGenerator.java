package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.BaseDaoModel;
import com.clever4j.rdbgenerator.codemodel.DaoModel;
import com.clever4j.rdbgenerator.codemodel.RecordFieldModel;
import com.clever4j.rdbgenerator.codemodel.RecordModel;
import com.clever4j.rdbgenerator.configuration.Database;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;
import freemarker.ext.beans.GenericObjectModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllNonnullByDefault
public class DaoGenerator {

    private final DaoModel daoModel;
    private final Path output;
    private final TemplateProcessor templateProcessor;

    public DaoGenerator(
        DaoModel daoModel,
        Path output,
        TemplateProcessor templateProcessor
    ) {
        this.daoModel = daoModel;
        this.output = output;
        this.templateProcessor = templateProcessor;
    }

    public void generate() {
        Map<String, Object> model = new HashMap<>();

        model.put("name", daoModel.name());
        model.put("packageName", daoModel.packageName());
        model.put("simpleName", daoModel.simpleName());
        model.put("recordModel", daoModel.recordModel());
        model.put("baseDaoModel", daoModel.baseDaoModel());
        model.put("database", daoModel.database());

        templateProcessor.processDaoTemplate(model, "%s/%s.java".formatted(output, daoModel.simpleName()));
    }
}
