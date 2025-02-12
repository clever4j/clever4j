package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.CodeModel;
import com.clever4j.rdbgenerator.codemodel.DaoModel;
import com.clever4j.rdbgenerator.codemodel.RecordModel;
import com.clever4j.rdbgenerator.configuration.Repository;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

@AllNonnullByDefault
public final class RepositoryCodeGenerator {

    private final Repository repository;
    private final CodeModel codeModel;
    private final TemplateProcessor templateProcessor;

    public RepositoryCodeGenerator(Repository repository, CodeModel codeModel, TemplateProcessor templateProcessor) {
        this.repository = repository;
        this.codeModel = codeModel;
        this.templateProcessor = templateProcessor;
    }

    public void run() {
        // records -----------------------------------------------------------------------------------------------------
        for (RecordModel record : codeModel.records()) {
            RecordGenerator recordGenerator = new RecordGenerator(
                record,
                repository.recordGenerator().output(),
                templateProcessor
            );

            recordGenerator.generate();
        }

        // dao ---------------------------------------------------------------------------------------------------------
        for (DaoModel dao : codeModel.daos()) {
            if (repository.daoGenerator() == null) {
                continue;
            }

            DaoGenerator daoGenerator = new DaoGenerator(
                dao,
                repository.daoGenerator().output(),
                templateProcessor
            );

            daoGenerator.generate();
        }
    }
}