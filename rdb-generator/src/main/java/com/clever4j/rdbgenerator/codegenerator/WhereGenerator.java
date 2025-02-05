package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.WhereModel;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

import java.util.HashMap;
import java.util.Map;

@AllNonnullByDefault
public class WhereGenerator {

    private final WhereModel whereModel;
    private final TemplateProcessor templateProcessor;

    public WhereGenerator(WhereModel whereModel, TemplateProcessor templateProcessor) {
        this.whereModel = whereModel;
        this.templateProcessor = templateProcessor;
    }

    public void generate(String distinctionDirectory) {
        Map<String, Object> model = new HashMap<>();

        model.put("packageName", whereModel.packageName());
        model.put("name", whereModel.name());
        model.put("tableName", whereModel.tableName());
        model.put("fields", whereModel.fields());

        templateProcessor.processWhere(model, "%s/%s.java".formatted(distinctionDirectory, whereModel.name()));
    }
}
