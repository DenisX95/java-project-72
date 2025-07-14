package hexlet.code.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public final class UrlService {
    public static String createBaseUrl(String urlAddress) throws URISyntaxException, MalformedURLException {
        var uri = new URI(urlAddress).normalize();
        var url = uri.toURL();

        var protocol = url.getProtocol();
        var host = url.getHost();
        var port = url.getPort();
        return (port == -1)
                ? String.format("%s://%s", protocol, host)
                : String.format("%s://%s:%d", protocol, host, port);
    }

    private UrlService() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
