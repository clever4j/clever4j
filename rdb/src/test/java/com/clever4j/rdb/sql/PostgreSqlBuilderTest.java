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
    void buildInsert() throws SQLException {
        BuildContext context = new BuildContext();

        Insert insert = Insert.build();
        insert.into("user");

        insert.value("user_id", 10);
        insert.value("name", "John");

        String sql = builder.build(insert, context);

        assertEquals("INSERT INTO \"user\" (\"user_id\", \"name\") VALUES (?, ?)", sql);

        assertEquals(2, context.getStatementObjects().size());
        assertEquals(10, context.getStatementObjects().getFirst());
        assertEquals("John", context.getStatementObjects().get(1));
    }

    @Test
    void buildUpdate() throws SQLException {
        BuildContext context = new BuildContext();

        Update update = Update.build();
        update.table("user");

        update.set("user_id", 10);
        update.set("name", "John");

        update.where().in("user_id", List.of(1, 2, 3));

        String sql = builder.build(update, context);

        assertEquals("UPDATE \"user\" SET \"user_id\" = ?, \"name\" = ? WHERE \"user_id\" IN  (?, ?, ?)", sql);

        assertEquals(5, context.getStatementObjects().size());
        assertEquals(10, context.getStatementObjects().getFirst());
        assertEquals("John", context.getStatementObjects().get(1));
        assertEquals(1, context.getStatementObjects().get(2));
        assertEquals(2, context.getStatementObjects().get(3));
        assertEquals(3, context.getStatementObjects().get(4));
    }

    @Test
    void buildDelete() throws SQLException {
        BuildContext context = new BuildContext();

        Delete delete = Delete.build();
        delete.table("user");
        delete.where().in("user_id", List.of(1, 2, 3));

        String sql = builder.build(delete, context);

        System.out.printf("test");
        assertEquals("DELETE FROM \"user\" WHERE \"user_id\" IN  (?, ?, ?)", sql);

        assertEquals(1, context.getStatementObjects().get(0));
        assertEquals(2, context.getStatementObjects().get(1));
        assertEquals(3, context.getStatementObjects().get(2));
    }
}