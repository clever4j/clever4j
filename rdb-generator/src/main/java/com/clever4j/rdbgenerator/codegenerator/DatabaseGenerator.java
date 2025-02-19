package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.*;
import com.clever4j.rdbgenerator.configuration.Database;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

@AllNonnullByDefault
public final class DatabaseGenerator {

    private final Database database;
    private final CodeModel codeModel;
    private final TemplateProcessor templateProcessor;

    public DatabaseGenerator(Database database, CodeModel codeModel, TemplateProcessor templateProcessor) {
        this.database = database;
        this.codeModel = codeModel;
        this.templateProcessor = templateProcessor;
    }

    public void run() {
        // recordModel -------------------------------------------------------------------------------------------------
        for (RecordModel recordModel : codeModel.recordModels()) {
            new RecordGenerator(recordModel, templateProcessor)
                .generate();
        }

        // templateDaoModel --------------------------------------------------------------------------------------------
        for (DaoTemplateModel daoTemplateModel : codeModel.daoTemplateModels()) {
            new DaoTemplateGenerator(daoTemplateModel, templateProcessor)
                .generate();
        }

        // dao ------------------------------------------------------------------------------------------------------
        for (DaoModel dao : codeModel.daoModels()) {
            new DaoGenerator(dao, templateProcessor).generate();
        }

        // implementationDaoTemplateModel ------------------------------------------------------------------------------
        for (ImplementationDaoTemplateModel implementationDaoTemplateModel : codeModel.implementationDaoTemplateModels()) {
            new ImplementationDaoTemplateGenerator(implementationDaoTemplateModel, templateProcessor).generate();
        }

        // implementation-dao ---------------------------------------------------------------------------------------
        for (ImplementationDaoModel implementationDaoModel : codeModel.implementationDaoModels()) {
            new ImplementationDaoGenerator(implementationDaoModel, templateProcessor).generate();
        }
    }
}