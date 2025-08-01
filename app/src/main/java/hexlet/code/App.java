package hexlet.code;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.controller.MainController;
import hexlet.code.controller.UrlCheckController;
import hexlet.code.controller.UrlController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;

public class App {

    private static int getPort() {
        String port = System.getenv().getOrDefault("PORT", "8080");
        return Integer.parseInt(port);
    }

    private static String getJdbcUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL",
                "jdbc:h2:mem:project;MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
    }

    private static String readResourceFile(String fileName) throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream(fileName);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    public static Javalin getApp() throws IOException, SQLException {
        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getJdbcUrl());

        var dataSource = new HikariDataSource(hikariConfig);

        var sql = readResourceFile("schema.sql");
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        BaseRepository.dataSource = dataSource;

        // Настройка приложения
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte(createTemplateEngine()));
        });

        app.get(NamedRoutes.rootPath(), MainController::index);
        app.post(NamedRoutes.urlsPath(), UrlController::create);
        app.get(NamedRoutes.urlsPath(), UrlController::index);
        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);
        app.post(NamedRoutes.urlCheckPath("{id}"), UrlCheckController::check);

        return app;
    }

    public static void main(String[] args) throws SQLException, IOException {
        getApp().start(getPort());
    }
}
