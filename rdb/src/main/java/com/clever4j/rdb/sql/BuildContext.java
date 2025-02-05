package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllNonnullByDefault
public class BuildContext {

    private final List<Object> statementObjects = new ArrayList<>();

    public void addStatementObjects(Object value) {
        this.statementObjects.add(value);
    }

    public List<Object> getStatementObjects() {
        return statementObjects;
    }

    public void prepareStatement(PreparedStatement statement) {
        try {
            for (int i = 0; i < statementObjects.size(); i++) {
                statement.setObject(i + 1, statementObjects.get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}