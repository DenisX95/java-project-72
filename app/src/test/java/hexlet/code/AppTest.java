package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

final class AppTest {

    private static Javalin app;
    private static String baseUrl;

    @BeforeAll
    static void beforeAll() throws Exception {
        app = App.getApp();
        app.start(0);
        baseUrl = "http://localhost:" + app.port();
    }

    @AfterAll
    static void afterAll() {
        app.stop();
    }

    @BeforeEach
    void prepareData() throws Exception {
        UrlRepository.removeAll();
    }

    @Test
    void rootPage() {

        HttpResponse<String> response = Unirest.get(baseUrl + NamedRoutes.rootPath()).asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).contains("Анализатор страниц");
    }

    @Test
    void listUrls() throws SQLException {
        Unirest.post(baseUrl + NamedRoutes.urlsPath()).field("url", "https://ru.hexlet.io/").asEmpty();
        Unirest.post(baseUrl + NamedRoutes.urlsPath()).field("url", "https://github.com/").asEmpty();

        HttpResponse<String> response = Unirest.get(baseUrl + NamedRoutes.urlsPath()).asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).contains("https://ru.hexlet.io")
                .contains("https://github.com");
    }

    @Test
    void showUrl() throws SQLException {
        Unirest.post(baseUrl + NamedRoutes.urlsPath()).field("url", "https://ru.hexlet.io/").asEmpty();
        Unirest.post(baseUrl + NamedRoutes.urlsPath()).field("url", "https://github.com/").asEmpty();

        var id = UrlRepository.getEntities().get(1).getId();
        HttpResponse<String> response = Unirest.get(baseUrl + NamedRoutes.urlPath(id)).asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).contains("https://github.com");
    }

    @Test
    void createUrl() {
        String newUrl = "https://docs.oracle.com/javase/tutorial/java/javaOO/classes.html";
        HttpResponse<?> response = Unirest.post(baseUrl + NamedRoutes.urlsPath())
                .field("url", newUrl)
                .asEmpty();
        assertThat(response.getStatus()).isEqualTo(302);

        HttpResponse<String> redirected = Unirest.get(baseUrl + NamedRoutes.urlsPath()).asString();
        assertThat(redirected.getBody()).contains("Страница успешно добавлена");
        assertThat(redirected.getBody()).contains("https://docs.oracle.com");

        HttpResponse<String> newResponse = Unirest.get(baseUrl + NamedRoutes.urlsPath()).asString();
        assertThat(newResponse.getBody()).doesNotContain("Страница успешно добавлена");
    }

    @Test
    void createInvalidUrl() {
        HttpResponse<?> response = Unirest.post(baseUrl + NamedRoutes.urlsPath())
                .field("url", "some-url")
                .asEmpty();
        assertThat(response.getStatus()).isEqualTo(302);

        HttpResponse<String> redirected = Unirest.get(baseUrl + NamedRoutes.urlsPath()).asString();
        assertThat(redirected.getBody()).contains("Некорректный URL");
    }

    @Test
    void createDuplicateUrl() throws SQLException {
        Unirest.post(baseUrl + NamedRoutes.urlsPath()).field("url", "https://github.com/").asEmpty();
        HttpResponse<?> response = Unirest.post(baseUrl + NamedRoutes.urlsPath())
                .field("url", "https://github.com/")
                .asEmpty();
        assertThat(response.getStatus()).isEqualTo(302);

        HttpResponse<String> redirected = Unirest.get(baseUrl + NamedRoutes.urlsPath()).asString();
        assertThat(redirected.getBody()).contains("Страница уже существует");
    }

    @Test
    void checkUrlIsOK() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            String mockHtml = """
                    <html>
                        <head>
                            <title>Mock Page</title>
                            <meta name="description" content="Mock description">
                        </head>
                        <body>
                            <h1>Mock H1 title</h1>
                            <div>Another Mock Contents</div>
                        </body>
                    </html>
                    """;

            server.enqueue(new MockResponse()
                    .setBody(mockHtml)
                    .setResponseCode(200)
                    .addHeader("Content-Type", "text/html"));

            server.start();

            var mockUrl = server.url("/").toString();
            Unirest.post(baseUrl + NamedRoutes.urlsPath()).field("url", mockUrl).asEmpty();
            var urlId = UrlRepository.getEntities().getFirst().getId();

            HttpResponse<?> response = Unirest.post(baseUrl + NamedRoutes.urlCheckPath(urlId)).asEmpty();
            assertThat(response.getStatus()).isEqualTo(302);

            HttpResponse<String> redirected = Unirest.get(baseUrl + NamedRoutes.urlPath(urlId)).asString();
            assertThat(redirected.getBody()).contains("Страница успешно проверена");

            var checks = UrlCheckRepository.find(urlId);
            assertThat(checks).hasSize(1);

            var check = checks.getFirst();
            assertThat(check.getStatusCode()).isEqualTo(200);
            assertThat(check.getTitle()).isEqualTo("Mock Page");
            assertThat(check.getH1()).isEqualTo("Mock H1 title");
            assertThat(check.getDescription()).isEqualTo("Mock description");
        }
    }

    @Test
    void checkUrlIsForbidden() throws Exception {
        try (MockWebServer server = new MockWebServer()) {
            String mockHtml = """
                    <html>
                        <head>
                            <title>Just a moment...</title>
                        </head>
                        <body>
                        </body>
                    </html>
                    """;

            server.enqueue(new MockResponse()
                    .setBody(mockHtml)
                    .setResponseCode(403)
                    .addHeader("Content-Type", "text/html"));

            server.start();

            var mockUrl = server.url("/").toString();
            Unirest.post(baseUrl + NamedRoutes.urlsPath()).field("url", mockUrl).asEmpty();
            var urlId = UrlRepository.getEntities().getFirst().getId();

            HttpResponse<?> response = Unirest.post(baseUrl + NamedRoutes.urlCheckPath(urlId)).asEmpty();
            assertThat(response.getStatus()).isEqualTo(302);

            HttpResponse<String> redirected = Unirest.get(baseUrl + NamedRoutes.urlPath(urlId)).asString();
            assertThat(redirected.getBody()).contains("Страница успешно проверена");

            var checks = UrlCheckRepository.find(urlId);
            assertThat(checks).hasSize(1);

            var check = checks.getFirst();
            assertThat(check.getStatusCode()).isEqualTo(403);
            assertThat(check.getTitle()).isEqualTo("Just a moment...");
            assertThat(check.getH1()).isEqualTo("");
            assertThat(check.getDescription()).isEqualTo("");
        }
    }
}
