package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

import java.sql.SQLException;

@AllNonnullByDefault
public final class PostgreSqlBuilder {

    public String build(Select select, BuildContext context) throws SQLException {
        StringBuilder query = new StringBuilder();

        // columns -----------------------------------------------------------------------------------------------------
        query.append("SELECT ");

        for (int i = 0; i < select.columns.size(); i++) {
            if (i > 0) query.append(", ");

            Column column = select.columns.get(i);

            query.append(buildExpression(query, column.value));

            if (!column.alias.isEmpty()) {
                query.append(" AS ").append(column.alias);
            }
        }

        // from --------------------------------------------------------------------------------------------------------
        if (select.from == null) {
            throw new SQLException("From is not set");
        }

        query.append(" FROM ");
        buildExpression(query, select.from.value);

        if (!select.from.alias.isEmpty()) {
            query.append(" AS ").append(select.from.alias);
        }

        // where -------------------------------------------------------------------------------------------------------
        if (select.where != null) {
            query.append(" WHERE ");
            buildWhere(query, select.where, context);
        }

        // if (!where.isEmpty()) {
        //     query.append(" WHERE ").append(where.build(statementContext, qc));
        // }

        return query.toString();
    }

    private void buildExpression(StringBuilder query, Expression expression) {
        if (expression instanceof Identifier) {
            Identifier identifier = (Identifier) expression;

            if (!identifier.qualifier.isEmpty() && !identifier.identifier.isEmpty()) {
                query.append("\"%s\".\"%s\"".formatted(identifier.qualifier, identifier.identifier));
            } else if (!identifier.identifier.isEmpty()) {
                query.append("\"%s\"".formatted(identifier.identifier));
            } else {
                throw new RuntimeException("Unknown identifier type: " + identifier);
            }
        } else {
            throw new RuntimeException("Unknown expression type: " + expression);
        }
    }

    private String buildWhere(StringBuilder query, Where where, BuildContext context) {
        for (int i = 0; i < where.conditions.size(); i++) {
            Where.Condition condition = where.conditions.get(i);

            if (condition.operator().equals(RelationOperator.IN)) {
                buildExpression(query, condition.operant());
            }
        }
    }
}
