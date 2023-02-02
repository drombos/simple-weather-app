package org.example.http.query;

public record CityQuery(String city, String state, String country) implements ApiLocationQuery {

    @Override
    public String toQuery() {
        return city
                + extensionIfNotNullOrEmpty(state)
                + extensionIfNotNullOrEmpty(country);
    }

    @Override
    public String toString() {
        return city + " (" + state + ") " + country;
    }

    private String extensionIfNotNullOrEmpty(String s) {
        if (s == null || s.isBlank()) {
            return "";
        } else {
            return " " + s;
        }
    }
}
