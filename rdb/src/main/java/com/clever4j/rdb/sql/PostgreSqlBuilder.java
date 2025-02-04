package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdb.sql.Where.Condition;

import java.sql.SQLException;

@AllNonnullByDefault
public final class PostgreSqlBuilder {

    public String build(Expression expression, BuildContext context) throws SQLException {
        StringBuilder out = new StringBuilder();
        build(expression, out, context);
        return out.toString();
    }

    private void build(Expression expression, StringBuilder out, BuildContext context) throws SQLException {
        if (expression instanceof Select select) {
            buildSelect(select, out, context);
        } else if (expression instanceof Identifier identifier) {
            buildIdentifier(identifier, out, context);
        } else if (expression instanceof Where where) {
            buildWhere(where, out, context);
        } else if (expression instanceof ValueExpression valueExpression) {
            buildValueExpression(valueExpression, out, context);
        } else if (expression instanceof ValuesExpression valuesExpression) {
            buildValueExpression(valuesExpression, out, context);
        }
    }

    private void buildSelect(Select select, StringBuilder out, BuildContext context) throws SQLException {
        // columns -----------------------------------------------------------------------------------------------------
        out.append("SELECT ");

        for (int i = 0; i < select.columns.size(); i++) {
            if (i > 0) out.append(", ");

            Column column = select.columns.get(i);

            build(column.value, out, context);

            if (!column.alias.isEmpty()) {
                out.append(" AS ").append(column.alias);
            }
        }

        // from --------------------------------------------------------------------------------------------------------
        if (select.from == null) {
            throw new SQLException("From is not set");
        }

        out.append(" FROM ");
        build(select.from.value, out, context);

        if (!select.from.alias.isEmpty()) {
            out.append(" AS ").append(select.from.alias);
        }

        // where -------------------------------------------------------------------------------------------------------
        if (select.where != null) {
            out.append(" WHERE ");

            build(select.where, out, context);
        }
    }

    private void buildIdentifier(Identifier identifier, StringBuilder query, BuildContext context) {
        if (!identifier.qualifier.isEmpty() && !identifier.identifier.isEmpty()) {
            query.append("\"%s\".\"%s\"".formatted(identifier.qualifier, identifier.identifier));
        } else if (!identifier.identifier.isEmpty()) {
            query.append("\"%s\"".formatted(identifier.identifier));
        } else {
            throw new RuntimeException("Unknown identifier type: " + identifier);
        }
    }

    private void buildValueExpression(ValueExpression valueExpression, StringBuilder query, BuildContext context) {
        query.append("?");
        context.putStatementObjects(valueExpression.value);
    }

    private void buildValueExpression(ValuesExpression valuesExpression, StringBuilder query, BuildContext context) {
        for (int i = 0; i < valuesExpression.values.size(); i++) {
            Object value = valuesExpression.values.get(i);

            query.append("?");
            context.putStatementObjects(value);

            if (i < valuesExpression.values.size() - 1) {
                query.append(", ");
            }
        }
    }

    private void buildWhere(Where where, StringBuilder query, BuildContext context) throws SQLException {
        for (int i = 0; i < where.conditions.size(); i++) {
            Condition condition = where.conditions.get(i);

            if (isWherePartBrackets(condition.left())) {
                query.append(" (");
                build(condition.left(), query, context);
                query.append(")");
            } else {
                build(condition.left(), query, context);
            }

            if (condition.operator().equals(RelationOperator.EQUAL)) {
                query.append(" = ");
            } else if (condition.operator().equals(RelationOperator.IN)) {
                query.append(" IN ");
            }

            if (isWherePartBrackets(condition.right())) {
                query.append(" (");
                build(condition.right(), query, context);
                query.append(")");
            } else {
                build(condition.right(), query, context);
            }

            if (i < where.conditions.size() - 1) {
                query.append(" ").append(where.operator.name()).append(" ");
            }
        }
    }

    private boolean isWherePartBrackets(Expression expression) {
        return expression instanceof Where ||
            expression instanceof Select ||
            expression instanceof ValuesExpression;
    }
}
