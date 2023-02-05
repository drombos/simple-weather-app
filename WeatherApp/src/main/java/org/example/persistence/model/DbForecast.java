package org.example.persistence.model;

import org.example.http.client.Api;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "forecast")
public class DbForecast extends DbObject {
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;
    @Column(name = "avg_temp")
    private Double avgTemp;

    @Column(name = "avg_felt_temp")
    private Double avgFeltTemp;

    @Column(name = "precipitation_chance")
    private Double precipitationChance;

    @Column(name = "precipitation_type")
    @Enumerated(EnumType.STRING)
    private Precipitation precipitationType;

    @Column(name = "precipitation_volume")
    private Double precipitationVolume;

    @Column(name = "wind_direction")
    private String windDirection;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "wind_gust_speed")
    private Double windGustSpeed;

    @Column(name = "forecast_source")
    private String forecastSource;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private DbLocation location;

    public DbForecast() { }

    public DbForecast(LocalDate date, String description, Double avgTemp, Double avgFeltTemp,
                      Double precipitationChance, Precipitation precipitationType, Double precipitationVolume,
                      String windDirection, Double windSpeed, Double windGustSpeed, String forecastSource) {
        this.date = date;
        this.description = description;
        this.avgTemp = avgTemp;
        this.avgFeltTemp = avgFeltTemp;
        this.precipitationChance = precipitationChance;
        this.precipitationType = precipitationType;
        this.precipitationVolume = precipitationVolume;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.windGustSpeed = windGustSpeed;
        this.forecastSource = forecastSource;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(Double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public Double getAvgFeltTemp() {
        return avgFeltTemp;
    }

    public void setAvgFeltTemp(Double avgFeltTemp) {
        this.avgFeltTemp = avgFeltTemp;
    }

    public Double getPrecipitationChance() {
        return precipitationChance;
    }

    public void setPrecipitationChance(Double precipitationChance) {
        this.precipitationChance = precipitationChance;
    }

    public Precipitation getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(Precipitation precipitationType) {
        this.precipitationType = precipitationType;
    }

    public Double getPrecipitationVolume() {
        return precipitationVolume;
    }

    public void setPrecipitationVolume(Double precipitationVolume) {
        this.precipitationVolume = precipitationVolume;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getWindGustSpeed() {
        return windGustSpeed;
    }

    public void setWindGustSpeed(Double windGustSpeed) {
        this.windGustSpeed = windGustSpeed;
    }

    public String getForecastSource() {
        return forecastSource;
    }

    public void setForecastSource(String forecastSource) {
        this.forecastSource = forecastSource;
    }

    public DbLocation getLocation() {
        return location;
    }

    public void setLocation(DbLocation location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DbForecast that = (DbForecast) o;

        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
