package com.clever4j.rdbgenerator.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecordGenerator {

    @JsonProperty("package-name")
    private String packageName;

    @JsonProperty("output")
    private String output;

    @JsonProperty("exclude-regex")
    private String excludeRegex;

    private List<Data> records;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getExcludeRegex() {
        return excludeRegex;
    }

    public void setExcludeRegex(String excludeRegex) {
        this.excludeRegex = excludeRegex;
    }

    public List<Data> getRecords() {
        return records;
    }

    public void setRecords(List<Data> records) {
        this.records = records;
    }
}
