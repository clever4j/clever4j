package com.clever4j.rdb.metadata;

import com.clever4j.lang.AllNonnullByDefault;

import java.sql.Connection;

@AllNonnullByDefault
public final class MetadataProvider {

    private final Connection connection;

    public MetadataProvider(Connection connection) {
        this.connection = connection;
    }

}
