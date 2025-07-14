package hexlet.code.service;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.util.HtmlParser;
import hexlet.code.util.HttpDownloader;

public final class UrlCheckService {
    public static UrlCheck createUrlCheck(Url url) {
        HttpDownloader.HttpResponseData htmlData = HttpDownloader.download(url.getName());
        var htmlPage = HtmlParser.parse(htmlData.body());

        var code = htmlData.statusCode();
        var title = htmlPage.title();


        var h1Tag = htmlPage.selectFirst("h1");
        var h1 = "";
        if (h1Tag != null) {
            h1 = h1Tag.text();  // безопасно
        }

        var description = htmlPage.select("meta[name = description]").attr("content");
        var idUrl = url.getId();

        return new UrlCheck(
                code,
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
