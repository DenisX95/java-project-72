package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public final class AppTest {

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
        System.out.println("Запускается createUrl");
        UrlRepository.save(new Url("https://ru.hexlet.io"));
        UrlRepository.save(new Url("https://github.com"));

        HttpResponse<String> response = Unirest.get(baseUrl + NamedRoutes.urlsPath()).asString();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody()).contains("https://ru.hexlet.io")
                .contains("https://github.com");
    }

    @Test
    void showUrl() throws SQLException {
        UrlRepository.save(new Url("https://ru.hexlet.io"));
        UrlRepository.save(new Url("https://github.com"));

        long id = UrlRepository.getEntities().get(1).getId();
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
        UrlRepository.save(new Url("https://github.com"));

        HttpResponse<?> response = Unirest.post(baseUrl + NamedRoutes.urlsPath())
                .field("url", "https://github.com/")
                .asEmpty();
        assertThat(response.getStatus()).isEqualTo(302);

        HttpResponse<String> redirected = Unirest.get(baseUrl + NamedRoutes.urlsPath()).asString();
        assertThat(redirected.getBody()).contains("Страница уже существует");
    }

}
