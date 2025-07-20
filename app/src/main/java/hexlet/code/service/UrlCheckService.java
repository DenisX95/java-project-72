package hexlet.code.service;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class UrlCheckService {
    public static UrlCheck createUrlCheck(Url url) {
        var response = Unirest.get(url.getName()).asString();
        var statusCode = response.getStatus();
        var body = response.getBody();
        Document htmlPage = Jsoup.parse(body);
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
