package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public class BuildSqlException extends RuntimeException {

    public BuildSqlException(String message) {
        super(message);
    }
}
