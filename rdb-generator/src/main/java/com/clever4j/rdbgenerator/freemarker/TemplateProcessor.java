package com.clever4j.rdbgenerator.freemarker;

import com.clever4j.lang.AllNonnullByDefault;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TimeZone;

@AllNonnullByDefault
public final class TemplateProcessor {

    private final Configuration configuration;

    public TemplateProcessor() {
        configuration = new Configuration(Configuration.VERSION_2_3_34);

        configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
        configuration.setDefaultEncoding("UTF-8");
        // configuration.setOutputFormat(PlainTextOutputFormat.INSTANCE);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);
        configuration.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
    }

    public Template getTemplate(String name) {
        try {
            return configuration.getTemplate(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processRecordTemplate(Map<String, Object> model, String distinction) {
        process(model, "record.ftlh", distinction);
    }

    public void processWhere(Map<String, Object> model, String distinction) {
        process(model, "where.ftlh", distinction);
    }

    public void processWhereOperator(Map<String, Object> model, String distinction) {
        process(model, "where-operator.ftlh", distinction);
    }

    public void processDaoTemplate(Map<String, Object> model, String distinction) {
        process(model, "dao-v2.ftlh", distinction);
    }

    public void process(Map<String, Object> model, String template, String distinction) {
        try {
            Template template1 = configuration.getTemplate(template);

            try (FileWriter fileWriter = new FileWriter(distinction)) {
                template1.process(model, fileWriter);
            } catch (IOException | TemplateException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}