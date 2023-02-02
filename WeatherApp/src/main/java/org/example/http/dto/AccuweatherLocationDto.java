package org.example.http.dto;

import com.google.gson.annotations.SerializedName;

public class AccuweatherLocationDto implements LocationDto {
    @SerializedName("Key")
    private String locationKey;

    @SerializedName("LocalizedName")
    private String cityName;

    @SerializedName("Country")
    private CountryDto country;

    @SerializedName("AdministrativeArea")
    private AreaDto area;

    @SerializedName("GeoPosition")
    private GeoPositionDto geoPosition;

    public String getLocationKey() {
        return locationKey;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country.name;
    }

    public String getArea() {
        return area.type + " " + area.name;
    }

    public Double getLongitude() {
        return geoPosition.longitude;
    }

    public Double getLatitude() {
        return geoPosition.latitude;
    }

    @Override
    public boolean isProperlyFormed() {
        return locationKey != null
                && cityName != null
                && country != null
                && area != null
                && geoPosition != null;
    }

    @Override
    public String toString() {
        return "MIASTO (id: " + locationKey + ") " +
                cityName + "\n\t" +
                area.type + " " + area.name + ", " +
                country.name + "\n\t" +
                "dł. " + geoPosition.longitude + " / " +
                "szer. " + geoPosition.latitude + "\n";
    }

    @Override
    public String accuweatherLocationKey() {
        return locationKey;
    }

    @Override
    public Double openweatherLatitude() {
        return geoPosition.latitude;
    }

    @Override
    public Double openweatherLongitude() {
        return geoPosition.longitude;
    }

    private static class CountryDto {
        @SerializedName("LocalizedName")
        private String name;
    }

    private static class AreaDto {
        @SerializedName("LocalizedType")
        private String type;

        @SerializedName("LocalizedName")
        private String name;
    }

    private static class GeoPositionDto {
        @SerializedName("Longitude")
        private Double longitude;

        @SerializedName("Latitude")
        private Double latitude;
    }
}
