package com.clever4j.rdb.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {

    Connection getConnection(String databaseId);

    Connection getNewConnection(String databaseId);
}