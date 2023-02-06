package org.example.ui.thymeleaf;

public enum Path {
    ADD("/add", "add"),
    DISPLAY("/display", "display"),
    FORECAST("/forecast", "forecast");

    final String url;
    final String template;

    Path(String url, String template) {
        this.url = url;
        this.template = template;
    }

    static Path from(String uri) {
        if (uri.startsWith("/add")) {
            return ADD;
        }
        if (uri.startsWith("/display")) {
            return DISPLAY;
        }
        if (uri.startsWith("/forecast")) {
            return FORECAST;
        }

        return null;
    }
}
