package org.example.http.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AccuweatherForecastsDto implements ForecastsDto {
    @SerializedName("Headline")
    private HeadlineDto headline;

    @SerializedName("DailyForecasts")
    private List<DailyForecastDto> dailyForecasts;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("####   ACCUWEATHER   ####\n");
        sb.append(headline.description);

        dailyForecasts.forEach(f -> sb.append("\n\t")
                .append(f.date)
                .append("\t--- ")
                .append(f.temp.min.celsius)
                .append(" °C --- ")
                .append(f.day.description));
        return sb.toString();
    }

    @Override
    public boolean isProperlyFormed() {
        return headline != null && dailyForecasts != null;
    }

    @Override
    public int maxDayOffset() {
        return dailyForecasts.size();
    }

    public DailyForecastDto getDailyForecast(int dayOffset) {
        return dailyForecasts.get(dayOffset);
    }

    private static class HeadlineDto {
        @SerializedName("Text")
        private String description;

        @SerializedName("EffectiveDate")
        private String startDate;

        @SerializedName("EndDate")
        private String endDate;

        private HeadlineDto() { }

    }
    public static class DailyForecastDto {
        @SerializedName("Date")
        private String date;
        @SerializedName("Temperature")
        private TemperatureDto temp;

        @SerializedName("RealFeelTemperature")
        private TemperatureDto feltTemp;

        @SerializedName("Day")
        private DayNightDto day;

        @SerializedName("Night")
        private DayNightDto night;

        public String getDate() {
            return date;
        }

        public TemperatureDto getTemp() {
            return temp;
        }

        public TemperatureDto getFeltTemp() {
            return feltTemp;
        }

        public DayNightDto getDay() {
            return day;
        }

        public DayNightDto getNight() {
            return night;
        }

        public static class TemperatureDto {
            @SerializedName("Minimum")
            private TemperatureValueDto min;

            @SerializedName("Maximum")
            private TemperatureValueDto max;

            public Double getMin() {
                return min.celsius;
            }

            public Double getMax() {
                return max.celsius;
            }

            private static class TemperatureValueDto {
                @SerializedName("Value")
                private Double celsius;
            }
        }

        public static class DayNightDto {
            @SerializedName("LongPhrase")
            private String description;

            @SerializedName("RainProbability")
            private Double rainChance;

            @SerializedName("SnowProbability")
            private Double snowChance;

            @SerializedName("IceProbability")
            private Double iceChance;

            @SerializedName("Wind")
            private WindDto wind;

            @SerializedName("WindGust")
            private WindDto windGust;

            @SerializedName("TotalLiquid")
            private LiquidDto liquidVolume;

            public String getDescription() {
                return description;
            }

            public Double getPrecipitationChance() {
                return Stream.of(rainChance, snowChance, iceChance).max(Double::compare).get();
            }

            public String getPrecipitationType() {
                String type;
                if (rainChance > snowChance && rainChance > iceChance) {
                    type = "deszcz";
                    if (snowChance > 25.0) {
                        type += " ze śniegiem";
                    }
                } else if (snowChance > rainChance && snowChance > iceChance) {
                    type = "śnieg";
                    if (rainChance > 25.0) {
                        type += " z deszczem";
                    }
                } else {
                    type = "grad";
                }
                return type;
            }

            public Double getLiquidVolume() {
                return liquidVolume.mm;
            }

            public Double getWindSpeed() {
                return wind.speed.kmh;
            }

            public Double getGustSpeed() {
                return windGust.speed.kmh;
            }

            public String getWindDir() {
                return wind.direction.dir;
            }

            private static class WindDto {
                @SerializedName("Speed")
                private WindSpeedDto speed;

                @SerializedName("Direction")
                private WindDirectionDto direction;

                private static class WindSpeedDto {
                    @SerializedName("Value")
                    private Double kmh;
                }

                private static class WindDirectionDto {
                    @SerializedName("English")
                    private String dir;
                }
            }

            private static class LiquidDto {
                @SerializedName("Value")
                private Double mm;
            }

        }
    }
}
