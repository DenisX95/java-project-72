package hexlet.code.util;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;

public final class HttpDownloader {
    public record HttpResponseData(int statusCode, String body) {
    }

    public static HttpResponseData download(String url) throws UnirestException {
        var response = Unirest.get(url).asString();
        return new HttpResponseData(response.getStatus(), response.getBody());
    }

    private HttpDownloader(String url) {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
