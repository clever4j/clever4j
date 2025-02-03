package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@AllNonnullByDefault
public final class Select {

    List<Column> columns = new ArrayList<>();

    @Nullable
    From from;

    @Nullable
    Where where;

    private Select() {

    }

    public static Select build() {
        return new Select();
    }

    // column ----------------------------------------------------------------------------------------------------------
    public void column(String column) {
        this.columns.add(new Column(Identifier.of(column), ""));
    }

    public void column(String column, String alias) {
        this.columns.add(new Column(Identifier.of(column), alias));
    }

    public void clearColumns() {
        this.columns.clear();
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
        this.from = From.of(identifier);
        return this;
    }

    public Select from(String identifier, String alias) {
        this.from = From.of(identifier, alias);
        return this;
    }
}
