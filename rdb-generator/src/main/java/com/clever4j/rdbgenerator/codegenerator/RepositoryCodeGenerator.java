package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.CodeModel;
import com.clever4j.rdbgenerator.codemodel.DaoModel;
import com.clever4j.rdbgenerator.codemodel.ImplementationDaoModel;
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
        // recordModels -----------------------------------------------------------------------------------------------------
        for (RecordModel record : codeModel.recordModels()) {
            RecordGenerator recordGenerator = new RecordGenerator(
                record,
                repository.recordOutput(),
                templateProcessor
            );

            recordGenerator.generate();
        }

        // dao ---------------------------------------------------------------------------------------------------------
        for (DaoModel dao : codeModel.daoModels()) {
            DaoGenerator daoGenerator = new DaoGenerator(
                dao,
                repository.daoOutput(),
                templateProcessor
            );

            daoGenerator.generate();
        }

        // implementation-dao ------------------------------------------------------------------------------------------
        for (ImplementationDaoModel implementationDaoModel : codeModel.implementationDaoModels()) {
            ImplementationDaoGenerator implementationDaoGenerator = new ImplementationDaoGenerator(
                implementationDaoModel,
                repository.implementationDaoOutput(),
                templateProcessor
            );

            implementationDaoGenerator.generate();
        }
    }
}