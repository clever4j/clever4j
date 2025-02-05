package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

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
}