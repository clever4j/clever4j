package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public interface SqlBuilder {
    String build(Expression expression, BuildContext context);
}
