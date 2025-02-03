package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

import java.util.List;

@AllNonnullByDefault
public final class Where {

    private final LogicOperator operator;

    Where(LogicOperator operator) {
        this.operator = operator;
    }

    // where.build()
    // where.in(...)
    // where.like(...)
    // where.gte(...)
    // where.gte(...)
    // where.or()

    record Condition(
        String column,
        RelationOperator operator,
        Object value,
        List<Object> values
    ) {

    }
}
