package com.clever4j.rdbgenerator.repositorycodegenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.codemodel.CodeModel;
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
        // for (Record record : codeModel.records()) {
        //     RecordGenerator recordGenerator = new RecordGenerator(record, Services.templateProcessor());
        //     recordGenerator.generate();
        // }

        System.out.printf("test");
    }
}