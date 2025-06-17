package hexlet.code;

import io.javalin.Javalin;

public class App {

    public static Javalin getApp() {
        // Настройка приложения
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        app.get("/", ctx -> ctx.result("Hello World"));

        return app;
    }

    public static void main(String[] args) {
        getApp().start(7070);
    }
}
