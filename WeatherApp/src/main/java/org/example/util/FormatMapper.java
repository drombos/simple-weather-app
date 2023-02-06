package org.example.util;

import org.example.http.client.Api;
import org.example.http.dto.*;
import org.example.http.query.ApiForecastQuery;
import org.example.persistence.model.DbForecast;
import org.example.persistence.model.DbLocation;
import org.example.persistence.model.Precipitation;
import org.example.ui.ForecastDisplayFormat;

import java.time.*;
import java.util.Objects;


public class FormatMapper {
    public DbLocation apiToDb(LocationDto apiObj) throws ParsingException {
        if (AccuweatherLocationDto.class != apiObj.getClass()) {
            throw new ParsingException("Na razie tylko AccuweatherLocationDto jest obsługiwane przez Mapper");
        }

        AccuweatherLocationDto source = (AccuweatherLocationDto) apiObj;
        return new DbLocation(
                source.getCityName(),
                source.getArea(),
                source.getCountry(),
                source.getLongitude(),
                source.getLatitude(),
                source.getLocationKey()
        );
    }

    public ApiForecastQuery dbToApi(DbLocation dbLocation) {
        return new ApiForecastQuery() {
            @Override
            public String accuweatherLocationKey() {
                return dbLocation.getAccuweatherKey();
            }

            @Override
            public Double openweatherLatitude() {
                return dbLocation.getLatitude();
            }

            @Override
            public Double openweatherLongitude() {
                return dbLocation.getLongitude();
            }

            @Override
            public String toString() {
                return dbLocation.toString();
            }
        };
    }

    public DbForecast apiToDb(ForecastsDto apiObj, int dayOffset) throws ParsingException {
        if (dayOffset > apiObj.maxDayOffset()) {
            throw new ParsingException("Zażądano daty wybiegającej zbyt daleko do przodu.");
        }
        if (dayOffset < 0) {
            throw new ParsingException("Przeszłe daty nie są obsługiwane przez darmową wersję API.");
        }

        if (AccuweatherForecastsDto.class == apiObj.getClass()) {
            AccuweatherForecastsDto.DailyForecastDto dailyForecast =
                    ((AccuweatherForecastsDto) apiObj).getDailyForecast(dayOffset);
            return accuweatherForecastToDb(dailyForecast);
        }

        if (apiObj.getClass() == OpenweatherForecastsDto.class) {
            OpenweatherForecastsDto.DailyForecastDto dailyForecast =
                    ((OpenweatherForecastsDto) apiObj).getDailyForecast(dayOffset);
            return openweatherForecastToDb(dailyForecast);
        }

        throw new IllegalArgumentException("Podany format Dto nie jest obsługiwany.");
    }

    private DbForecast accuweatherForecastToDb(AccuweatherForecastsDto.DailyForecastDto forecast) {
        AccuweatherForecastsDto.DailyForecastDto.DayNightDto day = forecast.getDay();
        AccuweatherForecastsDto.DailyForecastDto.DayNightDto night = forecast.getNight();

        ZonedDateTime forecastDate = ZonedDateTime.parse(forecast.getDate());
        LocalDate date = forecastDate.toLocalDate();

        String description = "Dzień: " + day.getDescription() + " / Noc: " + night.getDescription();

        Double avgTemp = avg(forecast.getTemp().getMin(), forecast.getTemp().getMax());
        Double avgFeltTemp = avg(forecast.getFeltTemp().getMin(), forecast.getFeltTemp().getMax());

        Double precipitationChance = avg(day.getPrecipitationChance(), night.getPrecipitationChance());
        Precipitation precipitationType = Precipitation.fromAccuweather(
                day.getPrecipitationType(),
                night.getPrecipitationType()
        );
        Double precipitationVolume = avg(day.getLiquidVolume(), night.getLiquidVolume());

        String windDirection = avgWindDirectionSymbol(day.getWindDir(), night.getWindDir());
        Double windSpeed = avg(day.getWindSpeed(), night.getWindSpeed());
        Double windGustSpeed = avg(day.getGustSpeed(), night.getGustSpeed());

        String forecastSource = Api.ACCUWEATHER.name;

        return new DbForecast(
                date,
                description,
                avgTemp,
                avgFeltTemp,
                precipitationChance,
                precipitationType,
                precipitationVolume,
                windDirection,
                windSpeed,
                windGustSpeed,
                forecastSource
        );
    }

    private DbForecast openweatherForecastToDb(OpenweatherForecastsDto.DailyForecastDto forecast) {
        LocalDate date = Instant.ofEpochMilli(forecast.getEpochDate() * 1000)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        String description = forecast.getWeatherDescription();

        Double avgTemp = avg(forecast.getDayTemp(), forecast.getNightTemp());
        Double avgFeltTemp = avg(forecast.getDayFeltTemp(), forecast.getNightFeltTemp());

        Double precipitationChance = forecast.getPrecipitationChance() * 100.0;
        Double rain = forecast.getRainVolume();
        Double snow = forecast.getSnowVolume();
        Precipitation precipitationType = Precipitation.fromOpenweather(rain, snow);
        Double precipitationVolume =
                Objects.requireNonNullElse(rain, 0.0)
                        + Objects.requireNonNullElse(snow, 0.0);

        Double windAngle = forecast.getWindDirectionDeg();
        String windDirection = windAngleToSymbol(windAngle);
        Double windSpeed = forecast.getWindSpeed();
        Double windGustSpeed = forecast.getWindGust();

        String forecastSource = Api.OPENWEATHER.name;

        return new DbForecast(
                date,
                description,
                avgTemp,
                avgFeltTemp,
                precipitationChance,
                precipitationType,
                precipitationVolume,
                windDirection,
                windSpeed,
                windGustSpeed,
                forecastSource
        );
    }

    public ForecastDisplayFormat dbToDisplay(DbForecast apiObj) {
        String date = apiObj.getDate().toString();

        String description = apiObj.getDescription();

        String temp = "%.1f °C; odczuwalna %.1f °C"
                .formatted(
                        apiObj.getAvgTemp(),
                        apiObj.getAvgFeltTemp()
                );

        String precipitationMsg;
        if (apiObj.getPrecipitationType() == Precipitation.UNKNOWN) {
            precipitationMsg = "(brak danych)";
        } else {
            precipitationMsg = apiObj.getPrecipitationType().toDescription();
        }
        String precipitation = "%.0f%% szansy na %s (%.1f mm)"
                .formatted(
                        apiObj.getPrecipitationChance(),
                        precipitationMsg,
                        apiObj.getPrecipitationVolume()
                );

        String wind = "%s, prędkość %.0f km/h, w porywach %.0f km/h"
                .formatted(
                        parseWindDirectionSymbol(apiObj.getWindDirection()),
                        apiObj.getWindSpeed(),
                        apiObj.getWindGustSpeed()
                );

        String source = apiObj.getForecastSource();

        return new ForecastDisplayFormat(date, description, temp, precipitation, wind, source);
    }

    private String windAngleToSymbol(Double windAngle) {
        if (windAngle < 45 || windAngle >= 315) {
            return "N";
        }
        if (windAngle >= 45 && windAngle < 135) {
            return "E";
        }
        if (windAngle >= 135 && windAngle < 225) {
            return "S";
        }
        return "W";

    }

    private String parseWindDirectionSymbol(String windSymbol) {
        String[] parsedWind = new String[windSymbol.length()];

        for (int i = 0; i < windSymbol.length(); i++) {
            if (i < windSymbol.length() - 1) {
                parsedWind[i] = switch (windSymbol.charAt(i)) {
                    case 'W' -> "zachodnio";
                    case 'N' -> "północno";
                    case 'E' -> "wschodnio";
                    case 'S' -> "południowo";
                    default -> "";
                };
            } else {
                parsedWind[i] = switch (windSymbol.charAt(i)) {
                    case 'W' -> "zachodni";
                    case 'N' -> "północny";
                    case 'E' -> "wschodni";
                    case 'S' -> "południowy";
                    default -> "";
                };
            }
        }

        return String.join("-", parsedWind);
    }

    private String avgWindDirectionSymbol(String dayWind, String nightWind) {
        String windSymbol;
        if (!dayWind.equalsIgnoreCase(nightWind) && nightWind.length() + dayWind.length() <= 3) {
            windSymbol = dayWind + nightWind;
        } else {
            windSymbol = dayWind;
        }

        windSymbol = windSymbol.toUpperCase();
        return pruneDuplicateDirections(windSymbol);
    }

    private String pruneDuplicateDirections(String raw) {
        StringBuilder pruned = new StringBuilder();
        for (int i = 0; i < raw.length() - 1; i++) {
            if (raw.charAt(i) != raw.charAt(i + 1)) {
                pruned.append(raw.charAt(i));
            }
        }
        pruned.append(raw.charAt(raw.length() - 1));
        return pruned.toString();
    }

    private Double avg(Double v1, Double v2) {
        return 0.5 * (v1 + v2);
    }

    public static class ParsingException extends Exception {
        public ParsingException(String message) {
            super(message);
        }
    }


}
