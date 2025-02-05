package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.WhereOperatorModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class WhereOperatorGenerator {

    private final WhereOperatorModel whereOperatorModel;
    private final TemplateProcessor templateProcessor;

    public WhereOperatorGenerator(WhereOperatorModel whereOperatorModel, TemplateProcessor templateProcessor) {
        this.whereOperatorModel = whereOperatorModel;
        this.templateProcessor = templateProcessor;
    }

    public void generate(String distinctionDirectory) {
        Map<String, Object> model = new HashMap<>();

        model.put("packageName", whereOperatorModel.packageName());
        model.put("name", whereOperatorModel.name());

        templateProcessor.processWhereOperator(model, "%s/%s.java".formatted(distinctionDirectory, whereOperatorModel.name()));
    }
}
