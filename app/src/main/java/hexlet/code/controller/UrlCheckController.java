package hexlet.code.controller;

import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.service.UrlCheckService;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;

import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;

public final class UrlCheckController {

    public static void check(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

        var check = UrlCheckService.createUrlCheck(url);

        UrlCheckRepository.save(check);

        ctx.sessionAttribute("flash", "Страница успешно проверена");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect(NamedRoutes.urlPath(id));
    }

    private UrlCheckController() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
