package com.clever4j.rdbgenerator.freemarker;

import com.clever4j.fs.FsUtil;
import com.clever4j.lang.AllNonnullByDefault;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
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

    public void processRecordTemplate(Map<String, Object> model, String target) {
        process(model, "record.ftlh", target);
    }

    public void processBaseDaoTemplate(Map<String, Object> model, String target) {
        process(model, "base-dao.ftlh", target);
    }

    public void processDaoTemplate(Map<String, Object> model, String target) {
        process(model, "dao.ftlh", target);
    }

    public void processImplementationDaoTemplate(Map<String, Object> model, String target) {
        process(model, "implementation-dao-template.ftlh", target);
    }

    public void processImplementationDao(Map<String, Object> model, String target) {
        process(model, "implementation-dao.ftlh", target);
    }

    public void process(Map<String, Object> model, String template, String target) {
        try {
            Path targetPath = Path.of(target);

            Path parent = targetPath.getParent();

            if (parent == null) {
                throw new IllegalArgumentException("target path %s needs to have parent directory.".formatted(target));
            }

            FsUtil.createDirectory(parent);

            Template template1 = configuration.getTemplate(template);

            try (FileWriter fileWriter = new FileWriter(target)) {
                template1.process(model, fileWriter);
            } catch (IOException | TemplateException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}