package hexlet.code.util;

import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public final class HtmlParser {
    public static Document parse(String html) throws UnirestException {
        return Jsoup.parse(html);
    }

    private HtmlParser() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
