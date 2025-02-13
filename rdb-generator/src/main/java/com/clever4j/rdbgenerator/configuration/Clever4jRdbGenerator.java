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

        @JsonProperty("record-package-name")
        String recordPackageName,

        @JsonProperty("record-output")
        String recordOutput,

        @JsonProperty("dao-package-name")
        String daoPackageName,

        @JsonProperty("dao-output")
        String daoOutput,

        @JsonProperty("implementation-dao-package-name")
        String implementationDaoPackageName,

        @JsonProperty("implementation-dao-output")
        String implementationDaoOutput
    ) {
    }
}