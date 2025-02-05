package com.clever4j.rdb.sql;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostgreSqlBuilderTest {

    PostgreSqlBuilder builder = new PostgreSqlBuilder();

    @Test
    void buildSelect() throws SQLException {
        BuildContext context = new BuildContext();

        PostgreSqlBuilder builder = new PostgreSqlBuilder();

        Select select = Select.build();
        select.column("storage_object_id");
        select.column("name");
        select.from("user");
        select.where().in("user_id", List.of(1, 2, 3));

        String query = builder.build(select, context);
    }

    @Test
    void buildInset() throws SQLException {
        BuildContext context = new BuildContext();

        Insert insert = Insert.build();
        insert.into("user");

        insert.value("user_id", 10);
        insert.value("name", "John");

        String sql = builder.build(insert, context);

        assertEquals("INSERT INTO \"user\" (user_id, name) VALUES (?, ?)", sql);

        assertEquals(2, context.getStatementObjects().size());
        assertEquals(10, context.getStatementObjects().getFirst());
        assertEquals("John", context.getStatementObjects().get(1));
    }
}