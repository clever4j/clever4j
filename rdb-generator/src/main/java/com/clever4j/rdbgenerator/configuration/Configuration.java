package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@AllNonnullByDefault
public class Configuration {

    private Path file;
    private List<Repository> repositories = new ArrayList<>();

    public Configuration(Path file) throws IOException {
        this.file = file;

        load();
    }

    private void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        Clever4jRdbGenerator clever4jRdbGenerator = mapper.readValue(file.toFile(), Clever4jRdbGenerator.class);

        // repositories ------------------------------------------------------------------------------------------------
        if (clever4jRdbGenerator.repositories() == null || clever4jRdbGenerator.repositories().isEmpty()) {
            throw new RuntimeException("At least one repository is required");
        }

        for (Clever4jRdbGenerator.Repository repository : clever4jRdbGenerator.repositories()) {
            if (repository.id() == null || repository.id().isEmpty()) {
                throw new IllegalArgumentException("repository id is required");
            }

            if (repository.dbUrl() == null || repository.dbUrl().isEmpty()) {
                throw new IllegalArgumentException("repository.%s.dbUrl is required".formatted(repository.id()));
            }

            if (repository.dbUser() == null || repository.dbUser().isEmpty()) {
                throw new IllegalArgumentException("repository.%s.dbUser is required".formatted(repository.id()));
            }

            if (repository.dbPassword() == null || repository.dbPassword().isEmpty()) {
                throw new IllegalArgumentException("repository.%s.dbPassword is required".formatted(repository.id()));
            }

            // recordGenerator
            Clever4jRdbGenerator.Repository.RecordGenerator recordGenerator = repository.recordGenerator();

            if (recordGenerator == null) {
                throw new IllegalArgumentException("repository.%s.recordGenerator is required".formatted(repository.id()));
            }

            if (recordGenerator.packageName() == null || recordGenerator.packageName().isEmpty()) {
                throw new IllegalArgumentException("repository.%s.recordGenerator.packageName is required".formatted(repository.id()));
            }

            if (recordGenerator.output() == null || recordGenerator.output().isEmpty()) {
                throw new IllegalArgumentException("repository.%s.recordGenerator.output is required".formatted(repository.id()));
            }

            Path recordGeneratorOutput = Path.of(recordGenerator.output());

            if (!recordGeneratorOutput.isAbsolute()) {
                recordGeneratorOutput = file.getParent().resolve(recordGeneratorOutput);
            }

            // implementationDaoGenerator
            Clever4jRdbGenerator.Repository.DaoGenerator configDaoGenerator = repository.daoGenerator();

            DaoGenerator daoGenerator = null;

            if (configDaoGenerator != null) {
                if (configDaoGenerator.packageName() == null || configDaoGenerator.packageName().isEmpty()) {
                    throw new IllegalArgumentException("repository.%s.implementationDaoGenerator.packageName is required".formatted(repository.id()));
                }

                if (configDaoGenerator.output() == null || configDaoGenerator.output().isEmpty()) {
                    throw new IllegalArgumentException("repository.%s.implementationDaoGenerator.output is required".formatted(repository.id()));
                }

                Path daoGeneratorOutput = Path.of(configDaoGenerator.output());

                if (!daoGeneratorOutput.isAbsolute()) {
                    daoGeneratorOutput = file.getParent().resolve(daoGeneratorOutput);
                }

                daoGenerator = new DaoGenerator(
                    configDaoGenerator.packageName(),
                    daoGeneratorOutput
                );
            }

            // implementationDaoGenerator
            Clever4jRdbGenerator.Repository.ImplementationDaoGenerator configImplementationDaoGenerator = repository.implementationDaoGenerator();

            ImplementationDaoGenerator implementationDaoGenerator = null;

            if (configImplementationDaoGenerator != null) {
                if (configImplementationDaoGenerator.packageName() == null || configImplementationDaoGenerator.packageName().isEmpty()) {
                    throw new IllegalArgumentException("repository.%s.implementationDaoGenerator.packageName is required".formatted(repository.id()));
                }

                if (configImplementationDaoGenerator.output() == null || configImplementationDaoGenerator.output().isEmpty()) {
                    throw new IllegalArgumentException("repository.%s.implementationDaoGenerator.output is required".formatted(repository.id()));
                }

                Path implementationDaoGeneratorOutput = Path.of(configImplementationDaoGenerator.output());

                if (!implementationDaoGeneratorOutput.isAbsolute()) {
                    implementationDaoGeneratorOutput = file.getParent().resolve(implementationDaoGeneratorOutput);
                }

                implementationDaoGenerator = new ImplementationDaoGenerator(
                    configImplementationDaoGenerator.packageName(),
                    implementationDaoGeneratorOutput
                );
            }

            repositories.add(new Repository(
                repository.id(),
                repository.dbUrl(),
                repository.dbUser(),
                repository.dbPassword(),
                new RecordGenerator(
                    recordGenerator.packageName(),
                    recordGeneratorOutput
                ),
                daoGenerator,
                implementationDaoGenerator
            ));

            System.out.println("test");
        }
    }

    public List<Repository> getRepositories() {
        return repositories;
    }
}