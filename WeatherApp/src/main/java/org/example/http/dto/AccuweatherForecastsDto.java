package org.example.http.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    private static class HeadlineDto {
        @SerializedName("Text")
        private String description;

        @SerializedName("EffectiveDate")
        private String startDate;

        @SerializedName("EndDate")
        private String endDate;

        private HeadlineDto() {
        }
    }
    private static class DailyForecastDto {
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


        private static class TemperatureDto {
            @SerializedName("Minimum")
            private TemperatureValueDto min;

            @SerializedName("Maximum")
            private TemperatureValueDto max;


            private static class TemperatureValueDto {
                @SerializedName("Value")
                private Double celsius;
            }
        }

        private static class DayNightDto {
            @SerializedName("LongPhrase")
            private String description;

            @SerializedName("PrecipitationChance")
            private Double precipitationChance;

            @SerializedName("PrecipitationType")
            private String precipitationType;

            @SerializedName("Wind")
            private WindDto wind;

            @SerializedName("WindGust")
            private WindDto windGust;

            @SerializedName("TotalLiquid")
            private LiquidDto liquidVolume;


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
