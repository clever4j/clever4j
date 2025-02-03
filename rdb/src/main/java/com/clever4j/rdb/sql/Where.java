package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@AllNonnullByDefault
public final class Where implements Expression {

    final LogicOperator operator;
    final List<Condition> conditions = new ArrayList<>();

    Where(LogicOperator operator) {
        this.operator = operator;
    }

    public Where equal(Expression operant, Object value) {
        conditions.add(new Condition(operant, RelationOperator.EQUAL, value, null));
        return this;
    }

    public Where in(String identifier, List<Object> values) {
        conditions.add(new Condition(Identifier.of(identifier), RelationOperator.IN, null, values));
        return this;
    }

    public Where in(Expression operant, List<Object> values) {
        conditions.add(new Condition(operant, RelationOperator.EQUAL, null, values));
        return this;
    }

    record Condition(
        Expression operant,
        RelationOperator operator,

        @Nullable
        Object value,

        @Nullable
        List<Object> values
    ) {
    }
}
