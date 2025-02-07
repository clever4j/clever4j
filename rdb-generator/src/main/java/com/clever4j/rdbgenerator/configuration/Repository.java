package com.clever4j.rdbgenerator.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {

    private String id;

    @JsonProperty("db-url")
    private String dbUrl;

    @JsonProperty("db-user")
    private String dbUser;

    @JsonProperty("db-password")
    private String dbPassword;

    @JsonProperty("record-generator")
    private RecordGenerator recordGenerator;

    @JsonProperty("dao-generator")
    private Object daoGenerator;

    @JsonProperty("implementation-dao-generator")
    private Object implementationDaoGenerator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public RecordGenerator getRecordGenerator() {
        return recordGenerator;
    }

    public void setRecordGenerator(RecordGenerator recordGenerator) {
        this.recordGenerator = recordGenerator;
    }

    public Object getDaoGenerator() {
        return daoGenerator;
    }

    public void setDaoGenerator(Object daoGenerator) {
        this.daoGenerator = daoGenerator;
    }

    public Object getImplementationDaoGenerator() {
        return implementationDaoGenerator;
    }

    public void setImplementationDaoGenerator(Object implementationDaoGenerator) {
        this.implementationDaoGenerator = implementationDaoGenerator;
    }
}
