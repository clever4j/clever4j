package com.clever4j.rdbgenerator;

import com.clever4j.rdb.metadata.MetadataProvider;
import com.clever4j.rdb.metadata.Table;
import com.clever4j.rdbgenerator.configuration.TemplateConfiguration;
import com.clever4j.rdbgenerator.daogenerator.DaoGenerator;
import com.clever4j.rdbgenerator.recordgenerator.RecordGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    // Metoda do połączenia z bazą danych
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/traisit_db";
        String user = "traisit_db";
        String password = "traisit_db";

        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) throws SQLException, IOException, TemplateException {
        MetadataProvider metadataProvider = new MetadataProvider(getConnection());
        TypeMapper typeMapper = new TypeMapper();

        List<Table> tables = metadataProvider.getTables();
        TemplateConfiguration templateConfiguration = new TemplateConfiguration();
        String distinctionDirectory = "/home/workstati/desktop/traisit/traisit-core/src/main/java/com/traisit/domain/database/test";

        for (Table table : tables) {
            if (!table.name().equals("test_tag")) {
                continue;
            }

            // record
            String packageName = "com.traisit.domain.database.test";

            RecordGenerator recordGenerator = new RecordGenerator(
                packageName,
                table,
                templateConfiguration,
                typeMapper
            );

            recordGenerator.generate(distinctionDirectory);

            // dao
            DaoGenerator daoGenerator = new DaoGenerator(
                packageName,
                table,
                recordGenerator,
                templateConfiguration,
                typeMapper
            );

            daoGenerator.generate(distinctionDirectory);

            break;
        }
    }
}