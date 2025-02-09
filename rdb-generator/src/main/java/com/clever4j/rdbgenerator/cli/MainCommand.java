package com.clever4j.rdbgenerator.cli;

import com.clever4j.rdb.metadata.DatabaseMetadata;
import com.clever4j.rdb.metadata.MetadataLoader;
import com.clever4j.rdbgenerator.freemarker.TemplateProcessor;
import com.clever4j.rdbgenerator.repositorycodegenerator.RepositoryCodeGenerator;
import com.clever4j.rdbgenerator.codemodel.CodeModel;
import com.clever4j.rdbgenerator.codemodel.CodeModelLoader;
import com.clever4j.rdbgenerator.configuration.Configuration;
import com.clever4j.rdbgenerator.configuration.Repository;
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
        for (Repository repository : configuration.getRepositories()) {
            Connection connection = getConnection(repository);

            MetadataLoader metadataLoader = new MetadataLoader(connection);
            DatabaseMetadata databaseMetadata = metadataLoader.load();

            // for (TableMetadata table : databaseMetadata.tables()) {
            //     if (!table.name().equals("test_tag")) {
            //         continue;
            //     }
            // }

            CodeModelLoader codeModelLoader = new CodeModelLoader(repository, databaseMetadata);
            CodeModel codeModel = codeModelLoader.load();

            RepositoryCodeGenerator repositoryCodeGenerator = new RepositoryCodeGenerator(repository, codeModel, templateProcessor);
            repositoryCodeGenerator.run();
        }

        return 0;
    }

    private Connection getConnection(Repository repository) throws SQLException {
        return DriverManager.getConnection(
            repository.getDbUrl(),
            repository.getDbUser(),
            repository.getDbPassword()
        );
    }

    // public static void main(String[] args) throws SQLException, TemplateException, IOException {
    //     Connection connection = getConnection();
    //     TypeMapper typeMapper = new TypeMapper();
    //     ObjectNameProvider objectNameProvider = new ObjectNameProvider();

    //     CodeModelLoader codeModelLoader = new CodeModelLoader();

    //     String packageName = "com.traisit.domain.databasev2";

    //     CodeModel codeModel = codeModelLoader.load(
    //         packageName,
    //         connection,
    //         typeMapper,
    //         objectNameProvider
    //     );

    //     TemplateProcessor templateProcessor = new TemplateProcessor();
    //     String distinctionDirectory = "/home/inspipi/desktop/traisit/traisit-core/src/main/java/com/traisit/domain/databasev2";

    //     for (EntryCodeModel entry : codeModel.entries()) {
    //         RecordGenerator recordGenerator = new RecordGenerator(entry.record(), templateProcessor);
    //         recordGenerator.generate(distinctionDirectory);

    //         DaoGenerator daoGenerator = new DaoGenerator(
    //             entry.daoModel(),
    //             templateProcessor
    //         );

    //         daoGenerator.generate(distinctionDirectory);
    //     }
    // }
}