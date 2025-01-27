package com.clever4j.rdbgenerator;

import com.clever4j.rdbgenerator.codegenerator.DaoGenerator;
import com.clever4j.rdbgenerator.codegenerator.RecordGenerator;
import com.clever4j.rdbgenerator.codemodel.CodeModel;
import com.clever4j.rdbgenerator.codemodel.CodeModelLoader;
import com.clever4j.rdbgenerator.codemodel.EntryCodeModel;
import com.clever4j.rdbgenerator.codemodel.ObjectNameProvider;
import com.clever4j.rdbgenerator.configuration.TemplateProcessor;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    // Metoda do połączenia z bazą danych
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/traisit_db";
        String user = "traisit_db";
        String password = "traisit_db";

        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) throws SQLException, TemplateException, IOException {
        Connection connection = getConnection();
        TypeMapper typeMapper = new TypeMapper();
        ObjectNameProvider objectNameProvider = new ObjectNameProvider();

        CodeModelLoader codeModelLoader = new CodeModelLoader();

        String packageName = "com.traisit.domain.database.test";

        CodeModel codeModel = codeModelLoader.load(
            packageName,
            connection,
            typeMapper,
            objectNameProvider
        );

        TemplateProcessor templateProcessor = new TemplateProcessor();
        String distinctionDirectory = "/home/workstati/desktop/traisit/traisit-core/src/main/java/com/traisit/domain/database/test";

        for (EntryCodeModel entry : codeModel.entries()) {
            // if (!entry.recordModel().tableName().equals("test_tag")) {
            //     continue;
            // }

            RecordGenerator recordGenerator = new RecordGenerator(entry.recordModel(), templateProcessor);
            recordGenerator.generate(distinctionDirectory);

            DaoGenerator daoGenerator = new DaoGenerator(
                entry.daoModel(),
                templateProcessor
            );

            daoGenerator.generate(distinctionDirectory);
        }
    }
}