package com.clever4j.rdbgenerator;

import com.clever4j.rdb.metadata.MetadataProvider;
import com.clever4j.rdb.metadata.Table;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    // Metoda do połączenia z bazą danych
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/traisit_db";  // URL do Twojej bazy danych
        String user = "traisit_db";  // Twoje dane logowania
        String password = "traisit_db";  // Twoje dane logowania

        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        MetadataProvider metadataProvider = new MetadataProvider(getConnection());

        List<Table> tables = metadataProvider.getTables();

        for (Table table : tables) {
            RecordCodeGenerator recordCodeGenerator = new RecordCodeGenerator();
            recordCodeGenerator.generate(table, "/home/workstati/desktop/traisit/traisit-core/src/main/java/com/traisit/domain/database/test");

            DaoCodeGenerator daoCodeGenerator = new DaoCodeGenerator();
            daoCodeGenerator.generate(table, "/home/workstati/desktop/traisit/traisit-core/src/main/java/com/traisit/domain/database/test");

            break;
        }
    }
}