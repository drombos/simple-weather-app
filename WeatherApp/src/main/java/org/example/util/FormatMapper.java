package org.example.util;

import org.example.http.dto.*;
import org.example.http.query.ApiForecastQuery;
import org.example.persistence.model.DbLocation;
import org.example.ui.ForecastDisplayFormat;

import java.time.*;


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

    public ForecastDisplayFormat apiToDisplay(ForecastsDto apiObj, int dayOffset) throws ParsingException {
        if (dayOffset > apiObj.maxDayOffset()) {
            throw new ParsingException("Zażądano daty wybiegającej zbyt daleko do przodu");
        }

        if (apiObj.getClass() == AccuweatherForecastsDto.class) {
            AccuweatherForecastsDto.DailyForecastDto dailyForecast =
                    ((AccuweatherForecastsDto) apiObj).getDailyForecast(dayOffset);
            return accuweatherToDisplay(dailyForecast);
        }
        if (apiObj.getClass() == OpenweatherForecastsDto.class) {
            OpenweatherForecastsDto.DailyForecastDto dailyForecast =
                    ((OpenweatherForecastsDto) apiObj).getDailyForecast(dayOffset);
            return openweatherToDisplay(dailyForecast);
        }

        throw new IllegalArgumentException("Podany format Dto nie jest obsługiwany.");
    }

    private ForecastDisplayFormat accuweatherToDisplay(AccuweatherForecastsDto.DailyForecastDto forecast) {
        AccuweatherForecastsDto.DailyForecastDto.DayNightDto day = forecast.getDay();
        AccuweatherForecastsDto.DailyForecastDto.DayNightDto night = forecast.getNight();

        ZonedDateTime forecastDate = ZonedDateTime.parse(forecast.getDate());
        String date = forecastDate.toLocalDate().toString();

        String description = "Dzień: " + day.getDescription() + " / Noc: " + night.getDescription();

        Double avgFeltTemp = 0.5 * (forecast.getFeltTemp().getMin() + forecast.getFeltTemp().getMax());
        String temp = "%.1f ÷ %.1f °C; odczuwalna %.1f °C"
                .formatted(
                        forecast.getTemp().getMin(),
                        forecast.getTemp().getMax(),
                        avgFeltTemp
                );

        String precipitation = "za dnia %.0f%% szansy na %s (%.1f mm), w nocy %.0f%% szansy na %s (%.1f mm)"
                .formatted(
                        day.getPrecipitationChance(),
                        day.getPrecipitationType(),
                        day.getLiquidVolume(),
                        night.getPrecipitationChance(),
                        night.getPrecipitationType(),
                        night.getLiquidVolume()
                );

        String parsedWindDirection = parseWindDirectionSymbol(day.getWindDir(), night.getWindDir());
        Double avgWindSpeed = 0.5 * (day.getWindSpeed() + night.getWindSpeed());
        Double avgGustSpeed = 0.5 * (day.getGustSpeed() + night.getGustSpeed());
        String wind = "%s, prędkość %.0f km/h, w porywach %.0f km/h"
                .formatted(
                        parsedWindDirection,
                        avgWindSpeed,
                        avgGustSpeed
                );
        String source = "accuweather.com";

        return new ForecastDisplayFormat(date, description, temp, precipitation, wind, source);
    }

    private ForecastDisplayFormat openweatherToDisplay(OpenweatherForecastsDto.DailyForecastDto forecast) {
        LocalDate forecastDate = Instant.ofEpochMilli(forecast.getEpochDate() * 1000)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        String date = forecastDate.toString();

        String description = forecast.getWeatherDescription();

        String temp = "%.1f °C w dzień, %.1f °C w nocy; odczuwalna %.1f °C w dzień, %.1f °C w nocy"
                .formatted(
                        forecast.getDayTemp(),
                        forecast.getNightTemp(),
                        forecast.getDayFeltTemp(),
                        forecast.getNightFeltTemp()
                );
        String precipitationType;
        Double precipitationVolume;
        Double rain = forecast.getRainVolume();
        Double snow = forecast.getSnowVolume();

        String precipitation;

        if (rain == null && snow == null) {
            precipitation = "%.0f%% szansy na opady"
                    .formatted(
                            forecast.getPrecipitationChance()
                    );
        } else {
            if (rain != null && snow == null) {
                precipitationType = "deszcz";
                precipitationVolume = rain;
            } else if (rain == null) {
                precipitationType = "śnieg";
                precipitationVolume = snow;
            } else {
                if (rain >= snow) {
                    precipitationType = "deszcz";
                    precipitationVolume = rain;
                } else {
                    precipitationType = "śnieg";
                    precipitationVolume = snow;
                }
            }
            precipitation = "%.0f%% szansy na %s (%.1f mm)"
                    .formatted(
                            forecast.getPrecipitationChance(),
                            precipitationType,
                            precipitationVolume
                    );
        }


        Double windAngle = forecast.getWindDirectionDeg();
        String wind = "%s, prędkość %.0f km/h, w porywach %.0f km/h"
                .formatted(
                        parseWindDirectionAngle(windAngle),
                        forecast.getWindSpeed(),
                        forecast.getWindGust()
                );

        String source = "openweather.com";

        return new ForecastDisplayFormat(date, description, temp, precipitation, wind, source);
    }

    private String parseWindDirectionAngle(Double windAngle) {
        if (windAngle < 45 || windAngle >= 315) {
            return "północny";
        }
        if (windAngle >= 45 && windAngle < 135) {
            return "wschodni";
        }
        if (windAngle >= 135 && windAngle < 225) {
            return "południowy";
        }
        if (windAngle >= 225) {
            return "zachodni";
        }
        return "nieokreślony kierunek";
    }

    private String parseWindDirectionSymbol(String dayWind, String nightWind) {
        String engWind;
        if (!dayWind.equalsIgnoreCase(nightWind) && nightWind.length() + dayWind.length() <= 3) {
            engWind = dayWind + nightWind;
        } else {
            engWind = dayWind;
        }

        engWind = engWind.toUpperCase();
        engWind = pruneDuplicateDirections(engWind);

        String[] plWind = new String[engWind.length()];

        for (int i = 0; i < engWind.length(); i++) {
            if (i < engWind.length() - 1) {
                plWind[i] = switch (engWind.charAt(i)) {
                    case 'W' -> "zachodnio";
                    case 'N' -> "północno";
                    case 'E' -> "wschodnio";
                    case 'S' -> "południowo";
                    default -> "";
                };
            } else {
                plWind[i] = switch (engWind.charAt(i)) {
                    case 'W' -> "zachodni";
                    case 'N' -> "północny";
                    case 'E' -> "wschodni";
                    case 'S' -> "południowy";
                    default -> "";
                };
            }
        }

        return String.join("-", plWind);
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

    public static class ParsingException extends Exception {
        public ParsingException(String message) {
            super(message);
        }
    }
}
