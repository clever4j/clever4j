package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public final class From {

    private final Expression value;
    private final String alias;

    From(Expression value, String alias) {
        this.value = value;
        this.alias = alias;
    }
}
