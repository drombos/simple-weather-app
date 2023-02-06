package org.example.persistence.model;

public enum Precipitation {
    RAIN,
    SNOW,
    RAIN_MIXED,
    SNOW_MIXED,
    HAIL,
    UNKNOWN

    ;

    public static Precipitation fromAccuweather(String dayType, String nightType) {
        if (dayType.equalsIgnoreCase(nightType)) {
            return fromDescription(dayType);
        } else {
            if (dayType.equalsIgnoreCase("deszcz") || nightType.equalsIgnoreCase("deszcz")) {
                return RAIN_MIXED;
            }
            if (dayType.equalsIgnoreCase("śnieg") || nightType.equalsIgnoreCase("śnieg")) {
                return SNOW_MIXED;
            }
        }
        return fromDescription(dayType);
    }

    public static Precipitation fromOpenweather(Double rain, Double snow) {
        if (rain == null && snow == null) {
            return UNKNOWN;
        }
        if (rain != null && snow == null) {
            return RAIN;
        } else if (rain == null) {
            return SNOW;
        } else {
            if (rain >= snow) {
                if (snow > 3.0) {
                    return RAIN_MIXED;
                } else {
                    return RAIN;
                }
            } else {
                if (rain > 3.0) {
                    return SNOW_MIXED;
                } else {
                    return SNOW;
                }
            }
        }
    }

    public String toDescription() {
        return switch(this) {
            case RAIN -> "deszcz";
            case SNOW -> "śnieg";
            case RAIN_MIXED -> "deszcz ze śniegiem";
            case SNOW_MIXED -> "śnieg z deszczem";
            case HAIL -> "grad";
            case UNKNOWN -> "?";
        };
    }

    private static Precipitation fromDescription(String type) {
        return switch (type.toLowerCase()) {
            case "deszcz" -> RAIN;
            case "śnieg" -> SNOW;
            case "deszcz ze śniegiem" -> RAIN_MIXED;
            case "śnieg z deszczem" -> SNOW_MIXED;
            case "grad" -> HAIL;
            default -> UNKNOWN;
        };
    }
}
