package org.example.http.query;

import java.util.Locale;

public record GeoQuery(Double latitude, Double longitude) implements ApiLocationQuery {
    @Override
    public String toQuery() {
        return String.format(Locale.US, "%.3f,%.3f", latitude, longitude);
    }

    @Override
    public String toString() {
        return "dł. " + latitude + ", szer. " + longitude;
    }
}