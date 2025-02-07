package com.clever4j.rdbgenerator.services;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.configuration.Configuration;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;
import jakarta.annotation.Nullable;

@AllNonnullByDefault
public final class Services {

    @Nullable
    private static Configuration configuration;

    @Nullable
    private static TemplateProcessor templateProcessor;

    public static void setConfiguration(Configuration configuration) {
        Services.configuration = configuration;
    }

    public static Configuration configuration() {
        if (configuration == null) {
            throw new IllegalStateException("Configuration has not been set");
        }

        return configuration;
    }

    public static TemplateProcessor templateProcessor() {
        if (templateProcessor == null) {
            templateProcessor = new TemplateProcessor();
        }

        return templateProcessor;
    }

    // public static GeneratorExecutor generatorExecutor() {
    //     if (generatorExecutor == null) {
    //         generatorExecutor = new GeneratorExecutor();
    //     }

    //     return generatorExecutor;
    // }
}