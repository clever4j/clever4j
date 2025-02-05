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

    public Where equal(String identifier, Object value) {
        conditions.add(new Condition(Identifier.of(identifier), RelationOperator.EQUAL, new ValueExpression(value)));
        return this;
    }

    public Where in(String identifier, List<?> values) {
        conditions.add(new Condition(Identifier.of(identifier), RelationOperator.IN, new ValuesExpression(values)));
        return this;
    }

    record Condition(
        Expression left,
        RelationOperator operator,
        Expression right
    ) {
    }

    public interface WhereConfigurer {
        void configure(Where where);
    }
}
