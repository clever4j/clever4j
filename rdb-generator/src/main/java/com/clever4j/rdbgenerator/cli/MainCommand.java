package com.clever4j.rdbgenerator.cli;

import com.clever4j.rdbgenerator.configuration.Configuration;
import com.clever4j.rdbgenerator.services.Services;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.nio.file.Path;
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

        return 0;
    }

    // // Metoda do połączenia z bazą danych
    // private static Connection getConnection() throws SQLException {
    //     String url = "jdbc:postgresql://localhost:5432/traisit_db";
    //     String user = "traisit_db";
    //     String password = "traisit_db";

    //     return DriverManager.getConnection(url, user, password);
    // }

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
    //         RecordGenerator recordGenerator = new RecordGenerator(entry.recordModel(), templateProcessor);
    //         recordGenerator.generate(distinctionDirectory);

    //         DaoGenerator daoGenerator = new DaoGenerator(
    //             entry.daoModel(),
    //             templateProcessor
    //         );

    //         daoGenerator.generate(distinctionDirectory);
    //     }
    // }
}