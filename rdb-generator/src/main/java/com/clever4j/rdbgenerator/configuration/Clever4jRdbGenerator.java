package com.clever4j.rdbgenerator.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Clever4jRdbGenerator(
    @JsonProperty("option-a")
    boolean optionA,

    @JsonProperty("option-b")
    boolean optionB,

    List<Repository> repositories
) {
    public record Repository(
        String id,

        @JsonProperty("db-url")
        String dbUrl,

        @JsonProperty("db-user")
        String dbUser,

        @JsonProperty("db-password")
        String dbPassword,

        @JsonProperty("record-generator")
        RecordGenerator recordGenerator,

        @JsonProperty("dao-generator")
        Object daoGenerator,

        @JsonProperty("implementation-dao-generator")
        Object implementationDaoGenerator
    ) {
        public record RecordGenerator(
            @JsonProperty("package-name")
            String packageName,

            @JsonProperty("output")
            String output,

            @JsonProperty("exclude-regex")
            String excludeRegex,

            @JsonProperty("records")
            List<Record> records
        ) {

            public record Record(
                String id,
                List<Field> fields
            ) {
                public record Field(
                    String id,
                    String type
                ) {

                }
            }
        }
    }
}