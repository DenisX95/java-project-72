package hexlet.code.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlNormalizer {
    public static String normalize(String url) throws URISyntaxException, MalformedURLException {
        var uri = new URI(url).normalize();
        var normilizedUrl = uri.normalize().toURL();

        var protocol = normilizedUrl.getProtocol();
        var host = normilizedUrl.getHost();
        var port = normilizedUrl.getPort();
        return (port == -1)
                ? String.format("%s://%s", protocol, host)
                : String.format("%s://%s:%d", protocol, host, port);
    }

    private UrlNormalizer() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
