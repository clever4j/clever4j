package com.clever4j.rdbgenerator.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Clever4jRdbGenerator(
    @JsonProperty("option-a")
    boolean optionA,

    @JsonProperty("option-b")
    boolean optionB,

    List<Database> databases
) {
    public record Database(
        String id,

        @JsonProperty("db-url")
        String dbUrl,

        @JsonProperty("db-user")
        String dbUser,

        @JsonProperty("db-password")
        String dbPassword,

        @JsonProperty("exclude-tables")
        List<String> excludeTables,

        @JsonProperty("only-tables")
        List<String> onlyTables,

        @JsonProperty("package-name")
        String packageName,

        @JsonProperty("output")
        String output,

        // record ------------------------------------------------------------------------------------------------------
        @JsonProperty("record-package-name")
        String recordPackageName,

        @JsonProperty("record-output")
        String recordOutput,

        @JsonProperty("record-annotations")
        List<String> recordAnnotations,

        // dao ---------------------------------------------------------------------------------------------------------
        @JsonProperty("dao-package-name")
        String daoPackageName,

        @JsonProperty("dao-output")
        String daoOutput,

        @JsonProperty("dao-annotations")
        List<String> daoAnnotations,

        // implementation-dao ------------------------------------------------------------------------------------------
        @JsonProperty("implementation-dao-package-name")
        String implementationDaoPackageName,

        @JsonProperty("implementation-dao-output")
        String implementationDaoOutput,

        @JsonProperty("implementation-dao-annotations")
        List<String> implementationDaoAnnotations
    ) {
    }
}