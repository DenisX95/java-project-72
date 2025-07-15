package hexlet.code.service;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.util.HtmlParser;
import kong.unirest.Unirest;

public final class UrlCheckService {
    public static UrlCheck createUrlCheck(Url url) {
        var response = Unirest.get(url.getName()).asString();
        var statusCode = response.getStatus();
        var body = response.getBody();
        var htmlPage = HtmlParser.parse(body);
        var title = htmlPage.title();

        var h1Tag = htmlPage.selectFirst("h1");
        var h1 = "";
        if (h1Tag != null) {
            h1 = h1Tag.text();
        }

        var description = htmlPage.select("meta[name = description]").attr("content");
        var idUrl = url.getId();

        return new UrlCheck(
                statusCode,
                title,
                h1,
                description,
                idUrl
        );
    }

    private UrlCheckService() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
