package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.configuration.Configuration.Config.Repository;

@AllNonnullByDefault
public final class GeneratorExecutor {

    private final Repository repository;

    public GeneratorExecutor(Repository repository) {
        this.repository = repository;
    }

    public void run() {

        System.out.printf("test");
    }
}