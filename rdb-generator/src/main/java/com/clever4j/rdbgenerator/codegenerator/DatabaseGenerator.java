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
        // recordModel ------------------------------------------------------------------------------------------------------
        for (RecordModel recordModel : codeModel.recordModels()) {
            new RecordGenerator(recordModel, database.recordOutput(), templateProcessor)
                .generate();
        }

        // dao ---------------------------------------------------------------------------------------------------------
        for (BaseDaoModel baseDaoModel : codeModel.baseDaoModels()) {
            new BaseDaoGenerator(baseDaoModel, database.baseDaoOutput(), templateProcessor)
                .generate();
        }

        // dao ---------------------------------------------------------------------------------------------------------
        for (DaoModel dao : codeModel.daoModels()) {
            new DaoGenerator(dao, database.daoOutput(), templateProcessor)
                .generate();
        }

        // base-implementation-dao ---------------------------------------------------------------------------------
        for (BaseImplementationDaoModel baseImplementationDaoModel : codeModel.baseImplementationDaoModels()) {
            new BaseImplementationDaoGenerator(baseImplementationDaoModel, database.baseImplementationDaoOutput(),
                templateProcessor).generate();
        }

        // implementation-dao ------------------------------------------------------------------------------------------
        for (ImplementationDaoModel implementationDaoModel : codeModel.implementationDaoModels()) {
            new ImplementationDaoGenerator(implementationDaoModel, database.implementationDaoOutput(),
                templateProcessor).generate();
        }
    }
}