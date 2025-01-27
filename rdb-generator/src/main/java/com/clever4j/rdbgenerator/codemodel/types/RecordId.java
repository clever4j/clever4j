package com.clever4j.rdbgenerator.codemodel.types;

import com.clever4j.lang.AllNonnullByDefault;

@AllNonnullByDefault
public class RecordId {

    private final String value;

    private RecordId(String value) {
        this.value = value;
    }

    public static RecordId of(String value) {
        return new RecordId(value);
    }
}