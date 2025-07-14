package hexlet.code.service;

import java.net.URI;

public class UrlService {
    public static String createBaseUrl(String urlAddress) throws Exception {
        var uri = new URI(urlAddress).normalize();
        var url = uri.toURL();

        var protocol = url.getProtocol();
        var host = url.getHost();
        var port = url.getPort();
        return (port == -1)
                ? String.format("%s://%s", protocol, host)
                : String.format("%s://%s:%d", protocol, host, port);
    }
}
