package com.clever4j.rdb;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Metoda do połączenia z bazą danych
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/traisit_db";  // URL do Twojej bazy danych
        String user = "traisit_db";  // Twoje dane logowania
        String password = "traisit_db";  // Twoje dane logowania

        return DriverManager.getConnection(url, user, password);
    }

    // /home/inspipi/desktop/clever4j/rdb/src/main/java/com/clever4j/rdb
    // Metoda do pobierania metadanych o tabeli
    public static List<ColumnMetadata> getTableMetadata(String tableName) {
        List<ColumnMetadata> columns = new ArrayList<>();

        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            // Pobieranie metadanych o kolumnach tabeli
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                String columnType = resultSet.getString("TYPE_NAME");

                boolean isNullable = resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;

                columns.add(new ColumnMetadata(columnName, columnType, isNullable));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columns;
    }

    public static void main(String[] args) {
        String tableName = "plan";  // Nazwa tabeli, dla której chcesz pobrać metadane

        List<ColumnMetadata> columns = getTableMetadata(tableName);

        // Wypisanie wyników
        for (ColumnMetadata column : columns) {
            System.out.println(column);
        }
    }

    // Klasa reprezentująca metadane kolumny
    public static class ColumnMetadata {
        private String columnName;
        private String columnType;
        private boolean isNullable;

        public ColumnMetadata(String columnName, String columnType, boolean isNullable) {
            this.columnName = columnName;
            this.columnType = columnType;
            this.isNullable = isNullable;
        }

        @Override
        public String toString() {
            return "ColumnMetadata{" +
                "columnName='" + columnName + '\'' +
                ", columnType='" + columnType + '\'' +
                ", isNullable=" + isNullable +
                '}';
        }
    }
}
