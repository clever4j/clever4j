package com.clever4j.rdbgenerator.configuration;

import java.util.List;

public class Data {

    private String id;
    private List<Field> fields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
