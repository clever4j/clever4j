package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;
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
        Clever4jRdbGenerator config = mapper.readValue(file.toFile(), Clever4jRdbGenerator.class);

        this.repositories = config.getRepositories();
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

}
