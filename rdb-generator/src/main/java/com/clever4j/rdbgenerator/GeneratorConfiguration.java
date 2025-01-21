package com.clever4j.rdbgenerator;

import com.clever4j.lang.AllNonnullByDefault;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@AllNonnullByDefault
public class GeneratorConfiguration {

    public static void main(String[] args) throws IOException {
        // Yaml yaml = new Yaml();

        // try (InputStream inputStream = Files.newInputStream(Path.of("/home/inspipi/desktop/clever4j/rdb/src/main/resources/rdb-generator.yaml"))) {
        //     Map<String, Object> data = yaml.load(inputStream);
        //     System.out.println(data);
        // } catch (IOException e) {
        //     throw new RuntimeException(e);
        // }

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Config config = mapper.readValue(new File("/home/inspipi/desktop/clever4j/rdb/src/main/resources/rdb-generator.yaml"), Config.class);
        System.out.println(config);
    }

    public static class Config {
        private Map<String, TraisitDatabaseConfig> databases;

        public Map<String, TraisitDatabaseConfig> getDatabases() {
            return databases;
        }

        public void setDatabases(Map<String, TraisitDatabaseConfig> databases) {
            this.databases = databases;
        }
    }

    public static class TraisitDatabaseConfig {

        @JsonProperty("target-directory")
        private String targetDirectory;

        @JsonProperty("package-name")
        private String packageName;

        // Gettery i settery
        public String getTargetDirectory() {
            return targetDirectory;
        }

        public void setTargetDirectory(String targetDirectory) {
            this.targetDirectory = targetDirectory;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }
    }
}
