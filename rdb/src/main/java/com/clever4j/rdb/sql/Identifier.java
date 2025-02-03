package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public final class Identifier implements Expression {

    private final String qualifier;
    private final String identifier;

    Identifier(String qualifier, String identifier) {
        this.qualifier = qualifier;
        this.identifier = identifier;
    }
}
