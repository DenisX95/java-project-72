package hexlet.code.util;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpDownloader.class);

    public record HttpResponseData(int statusCode, String body) {
    }

    public static HttpResponseData download(String url) {
        try {
            var response = Unirest.get(url).asString();
            return new HttpResponseData(response.getStatus(), response.getBody());
        } catch (UnirestException e) {
            LOGGER.error("Ошибка при загрузке URL: {}", url, e);
            return new HttpResponseData(0, null);
        }
    }

    private HttpDownloader() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
