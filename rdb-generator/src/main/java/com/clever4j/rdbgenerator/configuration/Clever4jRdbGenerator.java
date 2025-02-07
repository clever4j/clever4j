package com.clever4j.rdbgenerator.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Clever4jRdbGenerator {

    @JsonProperty("option-a")
    private boolean optionA;

    @JsonProperty("option-b")
    private boolean optionB;

    private List<Repository> repositories;

    public boolean isOptionA() {
        return optionA;
    }

    public void setOptionA(boolean optionA) {
        this.optionA = optionA;
    }

    public boolean isOptionB() {
        return optionB;
    }

    public void setOptionB(boolean optionB) {
        this.optionB = optionB;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

}
