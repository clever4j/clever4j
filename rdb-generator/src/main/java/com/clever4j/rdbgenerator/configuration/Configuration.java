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
    private List<Database> repositories = new ArrayList<>();

    public Configuration(Path file) throws IOException {
        this.file = file;

        load();
    }

    private void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        Clever4jRdbGenerator clever4jRdbGenerator = mapper.readValue(file.toFile(), Clever4jRdbGenerator.class);

        // databases ------------------------------------------------------------------------------------------------
        if (clever4jRdbGenerator.databases() == null || clever4jRdbGenerator.databases().isEmpty()) {
            throw new RuntimeException("At least one repository is required");
        }

        for (Clever4jRdbGenerator.Database database : clever4jRdbGenerator.databases()) {
            String id = readString(database.id(), "repository.id is required");
            String dbUrl = readString(database.dbUrl(), "repository.%s.db-url is required".formatted(id));
            String dbUser = readString(database.dbUser(), "repository.%s.db-user is required".formatted(id));
            String dbPassword = readString(database.dbPassword(), "repository.%s.db-password is required".formatted(id));

            String recordPackageName = readString(database.recordPackageName(), "repository.%s.record-package-name is required".formatted(id));
            Path recordOutput = readPath(database.recordOutput(), "repository.%s.record-output is required".formatted(id));

            String daoPackageName = readString(database.daoPackageName(), "repository.%s.dao-package-name is required".formatted(id));
            Path daoOutput = readPath(database.daoOutput(), "repository.%s.dao-output is required".formatted(id));

            String implementationDaoPackageName = readString(database.implementationDaoPackageName(), "repository.%s.implementation-dao-package-name is required".formatted(id));
            Path implementationDaoOutput = readPath(database.implementationDaoOutput(), "repository.%s.implementation-dao-output is required".formatted(id));

            repositories.add(new Database(id, dbUrl, dbUser, dbPassword, recordPackageName, recordOutput,
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

    public List<Database> getRepositories() {
        return repositories;
    }
}