package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@AllNonnullByDefault
public final class Update implements Expression {

    @Nullable
    Identifier table;

    List<String> columns = new ArrayList<>();
    List<Object> values = new ArrayList<>();

    @Nullable
    Where where;

    Update() {

    }

    public static Update build() {
        return new Update();
    }

    // table ------------------------------------------------------------------------------------------------------------
    public Update table(String table) {
        this.table = Identifier.of(table);
        return this;
    }

    // set -----------------------------------------------------------------------------------------------------------
    public <T> Update set(String column, T value) {
        columns.add(column);
        values.add(value);
        return this;
    }

    // where -----------------------------------------------------------------------------------------------------------
    public Where where() {
        this.where = new Where(LogicOperator.AND);
        return this.where;
    }

    // -----------------------------------------------------------------------------------------------------------------
    public Update clear() {
        columns.clear();
        values.clear();
        return this;
    }
}