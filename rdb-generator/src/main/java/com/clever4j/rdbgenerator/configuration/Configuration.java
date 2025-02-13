package com.clever4j.rdbgenerator.configuration;

import com.clever4j.lang.AllNonnullByDefault;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import jakarta.annotation.Nullable;

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
            String id = readString(repository.id(), "repository.id is required");
            String dbUrl = readString(repository.dbUrl(), "repository.%s.db-url is required".formatted(id));
            String dbUser = readString(repository.dbUser(), "repository.%s.db-user is required".formatted(id));
            String dbPassword = readString(repository.dbPassword(), "repository.%s.db-password is required".formatted(id));

            String recordPackageName = readString(repository.recordPackageName(), "repository.%s.record-package-name is required".formatted(id));
            Path recordOutput = readPath(repository.recordOutput(), "repository.%s.record-output is required".formatted(id));

            String daoPackageName = readString(repository.daoPackageName(), "repository.%s.dao-package-name is required".formatted(id));
            Path daoOutput = readPath(repository.daoOutput(), "repository.%s.dao-output is required".formatted(id));

            String implementationDaoPackageName = readString(repository.implementationDaoPackageName(), "repository.%s.implementation-dao-package-name is required".formatted(id));
            Path implementationDaoOutput = readPath(repository.implementationDaoOutput(), "repository.%s.implementation-dao-output is required".formatted(id));

            repositories.add(new Repository(id, dbUrl, dbUser, dbPassword, recordPackageName, recordOutput,
                daoPackageName, daoOutput, implementationDaoPackageName, implementationDaoOutput));
        }
    }

    @Nullable
    private String readNullableString(@Nullable String value) {
        if (value == null) {
            return null;
        }

        value = value.strip();

        if (value.isEmpty()) {
            return null;
        }

        return value;
    }

    private String readString(@Nullable String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new RuntimeException(errorMessage);
        }

        return value.strip();
    }

    @Nullable
    private Path readNullablePath(@Nullable String value) {
        if (value == null) {
            return null;
        }

        value = value.strip();

        if (value.isEmpty()) {
            return null;
        }

        return Path.of(value);
    }

    private Path readPath(@Nullable String value, String errorMessage) {
        if (value == null || value.isBlank()) {
            throw new RuntimeException(errorMessage);
        }

        Path path = Path.of(value);

        if (path.isAbsolute()) {
            return path;
        }

        return file.getParent().resolve(path);
    }

    public List<Repository> getRepositories() {
        return repositories;
    }
}