package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public final class From {

    final Expression value;
    final String alias;

    From(Expression value, String alias) {
        this.value = value;
        this.alias = alias;
    }

    public static From of(String identifier) {
        return new From(Identifier.of(identifier), "");
    }

    public static From of(String identifier, String alias) {
        return new From(Identifier.of(identifier), alias);
    }
}