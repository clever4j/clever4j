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
        buildExpression(select.from.value, out, context);

        if (!select.from.alias.isEmpty()) {
            out.append(" AS ").append(select.from.alias);
        }

        // where -------------------------------------------------------------------------------------------------------
        // if (select.where != null) {
        //     out.append(" WHERE ");
        //     buildWhere(out, select.where, context);
        // }

        // if (!where.isEmpty()) {
        //     query.append(" WHERE ").append(where.build(statementContext, qc));
        // }
    }

    private void buildExpression(Expression expression, StringBuilder query, BuildContext context) {
        if (expression instanceof Identifier identifier) {
            buildIdentifier(identifier, query, context);
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

    private void buildWhere(Where where, StringBuilder query, BuildContext context) {
        query.append(" (");

        for (int i = 0; i < where.conditions.size(); i++) {
            Condition condition = where.conditions.get(i);

            buildExpression(condition.operant(), query, context);

            // if (condition.operator().equals(RelationOperator.EQUAL)) {
            //     query.append()
            // }

            // if (condition.operator().equals(RelationOperator.IN)) {
            //     buildExpression(query, condition.operant());
            // }
        }

        query.append(")");
    }
}
