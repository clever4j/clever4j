package com.clever4j.rdbgenerator.codegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.CodeModel;
import com.clever4j.rdbgenerator.codemodel.DaoModel;
import com.clever4j.rdbgenerator.codemodel.ImplementationDaoModel;
import com.clever4j.rdbgenerator.codemodel.RecordModel;
import com.clever4j.rdbgenerator.configuration.Database;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;

@AllNonnullByDefault
public final class RepositoryCodeGenerator {

    private final Database database;
    private final CodeModel codeModel;
    private final TemplateProcessor templateProcessor;

    public RepositoryCodeGenerator(Database database, CodeModel codeModel, TemplateProcessor templateProcessor) {
        this.database = database;
        this.codeModel = codeModel;
        this.templateProcessor = templateProcessor;
    }

    public void run() {

        // recordModels -----------------------------------------------------------------------------------------------------
        for (RecordModel record : codeModel.recordModels()) {
            RecordGenerator recordGenerator = new RecordGenerator(
                record,
                database.recordOutput(),
                templateProcessor
            );

            recordGenerator.generate();
        }

        // dao ---------------------------------------------------------------------------------------------------------
        for (DaoModel dao : codeModel.daoModels()) {
            DaoGenerator daoGenerator = new DaoGenerator(
                dao,
                database.daoOutput(),
                templateProcessor
            );

            daoGenerator.generate();
        }

        // implementation-dao ------------------------------------------------------------------------------------------
        for (ImplementationDaoModel implementationDaoModel : codeModel.implementationDaoModels()) {
            ImplementationDaoGenerator implementationDaoGenerator = new ImplementationDaoGenerator(
                implementationDaoModel,
                database.implementationDaoOutput(),
                templateProcessor
            );

            implementationDaoGenerator.generate();
        }
    }
}