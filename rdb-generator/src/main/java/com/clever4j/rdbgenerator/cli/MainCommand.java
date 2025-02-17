package com.clever4j.rdbgenerator.cli;

import com.clever4j.rdb.metadata.DatabaseMetadata;
import com.clever4j.rdb.metadata.MetadataLoader;
import com.clever4j.rdbgenerator.codemodel.CodeModel;
import com.clever4j.rdbgenerator.codemodel.CodeModelLoader;
import com.clever4j.rdbgenerator.configuration.Configuration;
import com.clever4j.rdbgenerator.configuration.Database;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;
import com.clever4j.rdbgenerator.codegenerator.DatabaseGenerator;
import com.clever4j.rdbgenerator.services.Services;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;

@Command(name = "clever4j-rdb-generator",
    mixinStandardHelpOptions = true,
    version = "1.0.0")
public final class MainCommand implements Callable<Integer> {

    @Option(names = {"--configuration-file"}, description = "Generator configuration file.")
    private Path configurationFile = Path.of("./clever4j-rdb-generator.yaml");

    public static void main(String... args) {
        int exitCode = new CommandLine(new MainCommand()).execute(args);

        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        Configuration configuration = new Configuration(configurationFile);

        Services.setConfiguration(configuration);

        TemplateProcessor templateProcessor = Services.templateProcessor();

        // Executor ----------------------------------------------------------------------------------------------------
        for (Database database : configuration.getRepositories()) {
            Connection connection = getConnection(database);

            MetadataLoader metadataLoader = new MetadataLoader(connection);
            DatabaseMetadata databaseMetadata = metadataLoader.load();

            CodeModelLoader codeModelLoader = new CodeModelLoader(database, databaseMetadata);
            CodeModel codeModel = codeModelLoader.load();

            DatabaseGenerator databaseGenerator = new DatabaseGenerator(database, codeModel, templateProcessor);
            databaseGenerator.run();
        }

        return 0;
    }

    private Connection getConnection(Database database) throws SQLException {
        return DriverManager.getConnection(
            database.dbUrl(),
            database.dbUser(),
            database.dbPassword()
        );
    }
}