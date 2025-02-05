package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@AllNonnullByDefault
public class Configuration {

    private final Path file;

    public Configuration(Path file) throws IOException {
        this.file = file;

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        Config config = mapper.readValue(file.toFile(), Config.class);

        System.out.println("test");
    }

    public static void main(String[] args) throws IOException {
        // Yaml yaml = new Yaml();

        // try (InputStream inputStream = Files.newInputStream(Path.of("/home/inspipi/desktop/clever4j/rdb/src/main/resources/rdb-generator.yaml"))) {
        //     Map<String, Object> data = yaml.load(inputStream);
        //     System.out.println(data);
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }

        // System.out.println(config);
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

            private List<Data> records;

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
