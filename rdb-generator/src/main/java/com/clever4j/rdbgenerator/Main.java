package com.clever4j.rdbgenerator;

import com.clever4j.rdb.metadata.MetadataProvider;
import com.clever4j.rdb.metadata.Table;
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
        DatabaseTypeMapper databaseTypeMapper = new DatabaseTypeMapper();

        List<Table> tables = metadataProvider.getTables();
        TemplateConfiguration templateConfiguration = new TemplateConfiguration();

        for (Table table : tables) {
            RecordGenerator recordGenerator = new RecordGenerator(
                "com.traisit.domain.database.test",
                table,
                templateConfiguration,
                databaseTypeMapper
            );

            recordGenerator.generate("/home/workstati/desktop/traisit/traisit-core/src/main/java/com/traisit/domain/database/test");
        }
    }
}