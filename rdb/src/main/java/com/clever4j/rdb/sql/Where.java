package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

import java.util.ArrayList;
import java.util.List;

@AllNonnullByDefault
public final class Where implements Expression {

    final LogicOperator operator;
    final List<Condition> conditions = new ArrayList<>();

    Where(LogicOperator operator) {
        this.operator = operator;
    }

    public Where equal(Expression left, Object right) {
        conditions.add(new Condition(left, RelationOperator.EQUAL, new ValueExpression(right)));
        return this;
    }

    public Where in(String left, List<?> values) {
        conditions.add(new Condition(Identifier.of(left), RelationOperator.IN, new ValuesExpression(values)));
        return this;
    }

    record Condition(
        Expression left,
        RelationOperator operator,
        Expression right
    ) {
    }
}
