package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;
import com.clever4j.rdbgenerator.configuration.Configuration.Config.Repository;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@AllNonnullByDefault
public class Configuration {

    private final Path file;
    private final List<Repository> repositories;

    public Configuration(Path file) throws IOException {
        this.file = file;

        // Load config
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Config config = mapper.readValue(file.toFile(), Config.class);

        this.repositories = config.repositories;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public static class Config {

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

        public static class Repository {

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

        public static class RecordGenerator {

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

        public static class Data {

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

        public static class Field {

            private String id;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
