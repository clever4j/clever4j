package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@AllNonnullByDefault
public final class Select implements Expression {

    List<Column> columns = new ArrayList<>();

    @Nullable
    From from;

    @Nullable
    Where where;

    Select() {

    }

    public static Select build() {
        return new Select();
    }

    // column ----------------------------------------------------------------------------------------------------------
    public Select column(String column) {
        columns.add(new Column(Identifier.of(column), ""));
        return this;
    }

    public Select column(String column, String alias) {
        columns.add(new Column(Identifier.of(column), alias));
        return this;
    }

    public Select clearColumns() {
        columns.clear();
        return this;
    }

    // where -----------------------------------------------------------------------------------------------------------
    public Where where() {
        where = new Where(LogicOperator.AND);
        return where;
    }

    public Where where(LogicOperator operator) {
        where = new Where(operator);
        return where;
    }

    // from ------------------------------------------------------------------------------------------------------------
    public Select from(String identifier) {
        this.from = new From(Identifier.of(identifier), "");
        return this;
    }

    public Select from(String identifier, String alias) {
        this.from = new From(Identifier.of(identifier), alias);
        return this;
    }
}
