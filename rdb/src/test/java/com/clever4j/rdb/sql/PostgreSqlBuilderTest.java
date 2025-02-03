package com.clever4j.rdb.sql;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class PostgreSqlBuilderTest {

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

        System.out.println(query);
    }
}