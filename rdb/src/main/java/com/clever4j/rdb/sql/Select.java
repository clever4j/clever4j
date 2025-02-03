package com.clever4j.rdb.sql;

import com.clever4j.lang.AllNonnullByDefault;

import java.util.ArrayList;
import java.util.List;

@AllNonnullByDefault
public final class Select {

    private final List<Column> colums = new ArrayList<>();
    private From from;

    public Select(From from) {
        this.from = from;
    }

    public Select (From from) {

    }

    public Select from(From from) {
        this.from = from;
        return this;
    }
}
