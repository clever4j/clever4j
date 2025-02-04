package com.clever4j.rdb.connection;

import java.sql.Connection;

public interface ConnectionProvider {

    Connection getConnection();

    Connection getNewConnection();
}