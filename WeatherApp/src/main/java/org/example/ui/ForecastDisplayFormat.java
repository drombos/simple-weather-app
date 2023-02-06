package org.example.ui;

public record ForecastDisplayFormat(
        String date,
        String description,
        String temp,
        String precipitation,
        String wind,
        String source
) {
    @Override
    public String toString() {
        return """
                %s:\t%s
                \tTEMP:\t%s
                \tOPADY:\t%s
                \tWIATR:\t%s
                \t\t(źródło: %s)
                """.stripIndent()
                .formatted(date, description, temp, precipitation, wind, source);
    }

    @Override
    public String date() {
        return date;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String temp() {
        return temp;
    }

    @Override
    public String precipitation() {
        return precipitation;
    }

    @Override
    public String wind() {
        return wind;
    }

    @Override
    public String source() {
        return source;
    }
}
