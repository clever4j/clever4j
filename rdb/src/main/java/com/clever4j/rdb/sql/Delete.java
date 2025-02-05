package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;
import jakarta.annotation.Nullable;

@AllNonnullByDefault
public final class Delete implements Expression {

    @Nullable
    Identifier table;

    @Nullable
    Where where;

    Delete() {

    }

    public static Delete build() {
        return new Delete();
    }

    // table ------------------------------------------------------------------------------------------------------------
    public Delete table(String table) {
        this.table = Identifier.of(table);
        return this;
    }

    // where -----------------------------------------------------------------------------------------------------------
    public Where where() {
        this.where = new Where(LogicOperator.AND);
        return this.where;
    }
}