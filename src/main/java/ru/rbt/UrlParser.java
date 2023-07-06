package ru.rbt;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParser {

    public static String authFormat(String username, String password) {
        return Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    public UrlParser(String url) {
        Pattern pattern = Pattern.compile("(https?://)([^:^/]*)(:\\d*)?(.*)?");
        Matcher matcher = pattern.matcher(url);

        if (!matcher.find())
            throw new Error("URL: \"" + url + "\" parser error");

        this.protocol = matcher.group(1);
        this.domain   = matcher.group(2);
        this.port     = matcher.group(3);
        this.uri      = matcher.group(4);
    }

    public String getProtocol() {
        return protocol;
    }
    public String getDomain() {
        return domain;
    }
    public String getPort() {
        return port;
    }
    public String getUri() {
        return uri;
    }

    private final String protocol;
    private final String domain;
    private final String port;
    private final String uri;
}
