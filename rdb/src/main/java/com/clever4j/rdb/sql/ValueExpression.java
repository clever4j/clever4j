package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public final class ValueExpression implements Expression {

    final Object value;

    ValueExpression(Object value) {
        this.value = value;
    }
}
