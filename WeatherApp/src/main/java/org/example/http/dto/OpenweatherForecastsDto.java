package org.example.http.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpenweatherForecastsDto implements ForecastsDto {
    @SerializedName("daily")
    private List<DailyForecastDto> dailyForecasts;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("####   OPENWEATHER   ####\n");
        dailyForecasts.forEach(f -> sb.append(f).append("\n"));

        return sb.toString();
    }

    @Override
    public boolean isProperlyFormed() {
        return dailyForecasts != null;
    }

    @Override
    public int maxDayOffset() {
        return dailyForecasts.size();
    }

    public DailyForecastDto getDailyForecast(int dayOffset) {
        return dailyForecasts.get(dayOffset);
    }

    public static class DailyForecastDto {
        @SerializedName("dt")
        private Long epochDate;

        @SerializedName("temp")
        private TempDto temp;

        @SerializedName("feels_like")
        private TempDto feltTemp;

        @SerializedName("wind_speed")
        private Double windSpeed;

        @SerializedName("wind_gust")
        private Double windGust;

        @SerializedName("wind_deg")
        private Double windDirectionDeg;

        @SerializedName("pop")
        private Double precipitationChance;

        @SerializedName("rain")
        private Double rainVolume;

        @SerializedName("snow")
        private Double snowVolume;

        @SerializedName("weather")
        private List<WeatherDescriptionDto> weatherDescription;

        public Long getEpochDate() {
            return epochDate;
        }

        public Double getDayTemp() {
            return temp.dayAvg;
        }

        public Double getNightTemp() {
            return temp.nightAvg;
        }

        public Double getDayFeltTemp() {
            return feltTemp.dayAvg;
        }

        public Double getNightFeltTemp() {
            return feltTemp.nightAvg;
        }

        public Double getWindSpeed() {
            return windSpeed;
        }

        public Double getWindGust() {
            if (windGust == null) {
                return windSpeed;
            }
            return windGust;
        }

        public Double getWindDirectionDeg() {
            return windDirectionDeg;
        }

        public Double getPrecipitationChance() {
            return precipitationChance;
        }

        public Double getRainVolume() {
            return rainVolume;
        }

        public Double getSnowVolume() {
            return snowVolume;
        }

        public String getWeatherDescription() {
            if (weatherDescription.isEmpty()) {
                return "";
            } else {
                return weatherDescription.get(0).msg;
            }
        }

        @Override
        public String toString() {
            String description = "";
            if (!weatherDescription.isEmpty()) {
                description = weatherDescription.get(0).msg;
            }
            return "\t" + epochDate
                    + "\t--- "
                    + temp.dayAvg
                    + " °C --- "
                    + description;
        }

        private static class TempDto {
            @SerializedName("day")
            private Double dayAvg;

            @SerializedName("night")
            private Double nightAvg;
        }

        private static class WeatherDescriptionDto {
            @SerializedName("description")
            private String msg;
        }
    }
}
