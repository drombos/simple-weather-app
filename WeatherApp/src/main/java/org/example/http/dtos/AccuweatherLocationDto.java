package org.example.http.dtos;

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
